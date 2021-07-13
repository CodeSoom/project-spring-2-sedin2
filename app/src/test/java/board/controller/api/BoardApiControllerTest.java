package board.controller.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardApiController.class)
class BoardApiControllerTest {

    private static final Long EXISTED_BOARD_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /api/boards 는")
    class Describe_create {

        @Nested
        @DisplayName("만약 유효한 게시글 생성 정보로 요청이 들어오면")
        class Context_with_valid_board_api_request {

            @Test
            @DisplayName("HttpStatus 201 Created 를 응답한다")
            void it_returns_created() throws Exception {
                mockMvc.perform(post("/api/boards"))
                        .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("GET /api/boards 는")
    class Describe_read_all {

        @Nested
        @DisplayName("만약 요청이 들어오면")
        class Context_with_request {

            @Test
            @DisplayName("HttpStatus 200 OK 를 응답한다")
            void it_returns_ok() throws Exception {
                mockMvc.perform(get("/api/boards"))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("GET /api/boards/{boardId} 는")
    class Describe_read {

        @Nested
        @DisplayName("만약 존재하는 게시글 id로 요청이 들어오면")
        class Context_with_existed_board_id {

            @Test
            @DisplayName("HttpStatus 200 OK 를 응답한다")
            void it_returns_ok() throws Exception {
                mockMvc.perform(get("/api/boards/" + EXISTED_BOARD_ID))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /api/boards/{boardId} 는")
    class Describe_update {

        @Nested
        @DisplayName("만약 존재하는 게시글 id와 유효한 게시글 수정 정보로 요청이 들어오면")
        class Context_with_existed_board_id_and_valid_board_modification_request {

            @Test
            @DisplayName("HttpStatus 200 OK 를 응답한다")
            void it_returns_ok() throws Exception {
                mockMvc.perform(patch("/api/boards/" + EXISTED_BOARD_ID))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /api/boards/{boardId} 는")
    class Describe_delete {

        @Nested
        @DisplayName("만약 존재하는 게시글 id로 요청이 들어오면")
        class Context_with_existed_board_id {

            @Test
            @DisplayName("HttpStatus 204 No Content 를 응답한다")
            void it_returns_ok() throws Exception {
                mockMvc.perform(delete("/api/boards/" + EXISTED_BOARD_ID))
                        .andExpect(status().isNoContent());
            }
        }
    }
}
