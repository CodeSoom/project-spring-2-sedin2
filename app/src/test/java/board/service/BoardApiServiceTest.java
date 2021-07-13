package board.service;

import board.domain.Board;
import board.network.request.BoardApiRequest;
import board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BoardApiServiceTest {

    private static final String BOARD_TITLE = "게시글 제목";
    private static final String BOARD_CONTENT = "게시글 내용";
    private static final String PREFIX_FOR_CREATE = "새 ";
    private static final String PREFIX_FOR_UPDATE = "수정한 ";
    private static final String USER_NAME = "mocha";
    private static final Long EXISTED_BOARD_ID = 1L;

    private BoardApiService boardApiService;

    private BoardRepository boardRepository = mock(BoardRepository.class);

    private Board board;

    private BoardApiRequest boardApiRequestForCreate;

    private BoardApiRequest boardApiRequestForUpdate;

    @BeforeEach
    void prepare() {
        boardApiService = new BoardApiService(boardRepository);

        board = Board.builder()
                .title(BOARD_TITLE)
                .content(BOARD_CONTENT)
                .createdAt(LocalDateTime.now())
                .createdBy(USER_NAME)
                .deleted(false)
                .build();

        given(boardRepository.findById(EXISTED_BOARD_ID)).willReturn(Optional.of(board));
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create_method {

        @Nested
        @DisplayName("만약 유효한 게시글 생성 정보가 주어지면")
        class Context_with_valid_board_api_request_for_making {

            @BeforeEach
            void prepareValidBoardApiRequest() {
                boardApiRequestForCreate = BoardApiRequest.builder()
                        .title(PREFIX_FOR_CREATE + BOARD_TITLE)
                        .content(PREFIX_FOR_CREATE + BOARD_CONTENT)
                        .build();

                Board createdBoard = Board.builder()
                        .title(PREFIX_FOR_CREATE + BOARD_TITLE)
                        .content(PREFIX_FOR_CREATE + BOARD_CONTENT)
                        .createdAt(LocalDateTime.now())
                        .createdBy(USER_NAME)
                        .deleted(false)
                        .build();

                given(boardRepository.save(any(Board.class))).willReturn(createdBoard);
            }

            @Test
            @DisplayName("새 게시글을 만들고 리턴한다")
            void it_returns_new_board() {
                Board newBoard = boardApiService.create(boardApiRequestForCreate);

                assertThat(newBoard).isNotNull();
                assertThat(newBoard.getTitle()).isEqualTo(PREFIX_FOR_CREATE + BOARD_TITLE);

                verify(boardRepository).save(any(Board.class));
            }
        }
    }

    @Nested
    @DisplayName("readAll 메소드는")
    class Describe_read_all_method {

        @Nested
        @DisplayName("만약 모든 게시글 조회 요청이 주어지면")
        class Context_with_call {

            @BeforeEach
            void prepare() {
                List<Board> boards = new ArrayList<>();
                boards.add(board);
                given(boardRepository.findAll()).willReturn(boards);
            }

            @Test
            @DisplayName("모든 게시글을 조회해 리턴한다")
            void it_returns_all_boards() {
                List<Board> boards = boardApiService.readAll();

                assertThat(boards).isNotEmpty();
                assertThat(boards.size()).isGreaterThanOrEqualTo(1);

                verify(boardRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("read 메소드는")
    class Describe_read__method {

        @Nested
        @DisplayName("만약 존재하는 게시글 id가 주어지면")
        class Context_with_existed_board_id {

            @Test
            @DisplayName("게시글 상세정보를 조회해 리턴한다")
            void it_returns_board_detail() {
                Board board = boardApiService.read(EXISTED_BOARD_ID);

                assertThat(board).isNotNull();
                assertThat(board.getTitle()).isEqualTo(BOARD_TITLE);

                verify(boardRepository).findById(EXISTED_BOARD_ID);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update_method {

        @Nested
        @DisplayName("만약 존재하는 게시글 id와 게시글 수정 정보가 주어지면")
        class Context_with_existed_board_id_and_board_api_request_for_update {

            @BeforeEach
            void prepareBoardApiRequestForUpdate() {
                boardApiRequestForUpdate = BoardApiRequest.builder()
                        .title(PREFIX_FOR_UPDATE + BOARD_TITLE)
                        .content(PREFIX_FOR_UPDATE + BOARD_CONTENT)
                        .build();

                Board updatedBoard = Board.builder()
                        .title(PREFIX_FOR_UPDATE + BOARD_TITLE)
                        .content(PREFIX_FOR_UPDATE + BOARD_CONTENT)
                        .modifiedAt(LocalDateTime.now())
                        .modifiedBy(USER_NAME)
                        .build();

                given(boardRepository.save(any(Board.class))).willReturn(updatedBoard);
            }

            @Test
            @DisplayName("게시글을 수정하고 리턴한다")
            void it_returns_updated_board() {
                Board updatedBoard = boardApiService.update(EXISTED_BOARD_ID, boardApiRequestForUpdate);

                assertThat(updatedBoard).isNotNull();
                assertThat(updatedBoard.getTitle()).isEqualTo(PREFIX_FOR_UPDATE + BOARD_TITLE);

                verify(boardRepository).findById(EXISTED_BOARD_ID);
                verify(boardRepository).save(any(Board.class));
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete_method {

        @Nested
        @DisplayName("만약 존재하는 게시글 id가 주어지면")
        class Context_with_existed_board_id {

            @BeforeEach
            void prepare() {
                Board deletedBoard = Board.builder()
                        .deleted(true)
                        .build();

                given(boardRepository.save(any(Board.class))).willReturn(deletedBoard);
            }

            @Test
            @DisplayName("게시글을 삭제하고 리턴한다")
            void it_returns_deleted_board() {
                Board deletedBoard = boardApiService.delete(EXISTED_BOARD_ID);

                assertThat(deletedBoard.isDeleted()).isEqualTo(true);

                verify(boardRepository).findById(EXISTED_BOARD_ID);
                verify(boardRepository).save(any(Board.class));
            }
        }
    }
}
