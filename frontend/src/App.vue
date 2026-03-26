
<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// API 기본 설정 (백엔드 주소에 맞게 수정하세요)
const API_URL = 'http://localhost:8080';

// 상태 관리
const boardList = ref([]); // 목록
const boardInput = ref({ title: '', contents: '' }); // 작성 데이터
const selectedBoardIdx = ref(null); // 현재 펼쳐진 글 ID
const currentBoard = ref({}); // 상세 데이터
const loadingDetail = ref(false);

// 1. 목록 가져오기
const getBoardList = async () => {
  try {
    const response = await axios.get(`${API_URL}/board/list`);
    boardList.value = response.data;
  } catch (error) {
    alert('목록을 불러오는데 실패했습니다.');
  }
};

// 2. 게시글 작성
const createBoard = async () => {
  // 유효성 검사 (content로 통일)
  if (!boardInput.value.title || !boardInput.value.contents) {
    alert('제목과 내용을 입력해주세요!');
    return;
  }

  try {
    // 백엔드로 데이터 전송
    await axios.post(`${API_URL}/board/reg`, boardInput.value);

    // 폼 초기화 (content로 통일)
    boardInput.value = { title: '', contents: '' };

    // ⭐ 중요: 목록 새로고침 (정의한 함수명인 getBoardList 호출)
    await getBoardList();

    alert('게시글이 등록되었습니다.');
  } catch (error) {
    console.error(error);
    alert('등록 중 오류가 발생했습니다.');
  }
};

// 3. 상세 내용 토글 및 조회
const toggleDetail = async (id) => {
  if (selectedBoardIdx.value === id) {
    selectedBoardIdx.value = null;
    return;
  }

  selectedBoardIdx.value = id;
  loadingDetail.value = true;

  try {
    const response = await axios.get(`${API_URL}/board/${id}`);
    currentBoard.value = response.data;
  } catch (error) {
    alert('상세 내용을 불러올 수 없습니다.');
  } finally {
    loadingDetail.value = false;
  }
};

onMounted(getBoardList);
</script>
<template>
  <div class="container">
    <h1>📝 게시판 프로젝트</h1>

    <section class="write-section">
      <h3>새 게시글 작성</h3>
      <div class="form">
        <input v-model="boardInput.title" placeholder="제목을 입력하세요" />
        <textarea v-model="boardInput.contents" placeholder="내용을 입력하세요"></textarea>
        <button @click="createBoard">등록하기</button>
      </div>
    </section>

    <hr />

    <section class="list-section">
      <h3>게시글 목록</h3>
      <ul>
        <li v-for="board in boardList" :key="board.idx" class="board-item">
          <div @click="toggleDetail(board.idx)" class="board-title">
            {{ board.title }}
            <span>{{ selectedBoardIdx === board.idx ? '▲' : '▼' }}</span>
          </div>

          <div v-if="selectedBoardIdx === board.idx" class="board-detail">
            <p v-if="loadingDetail">불러오는 중...</p>
            <div v-else>
              <p><strong>내용:</strong> {{ currentBoard.contents }}</p>
            </div>
          </div>
        </li>
      </ul>
    </section>
  </div>
</template>


<style scoped>
.container { max-width: 600px; margin: 0 auto; padding: 20px; font-family: sans-serif; }
.write-section .form { display: flex; flex-direction: column; gap: 10px; margin-bottom: 20px; }
.write-section input, .write-section textarea { padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
.write-section button { padding: 10px; background-color: #42b983; color: white; border: none; border-radius: 4px; cursor: pointer; }

.board-item { list-style: none; border-bottom: 1px solid #eee; padding: 10px 0; }
.board-title { cursor: pointer; font-weight: bold; display: flex; justify-content: space-between; }
.board-title:hover { color: #42b983; }

.board-detail { background: #f9f9f9; padding: 15px; margin-top: 10px; border-radius: 4px; border-left: 4px solid #42b983; }
</style>