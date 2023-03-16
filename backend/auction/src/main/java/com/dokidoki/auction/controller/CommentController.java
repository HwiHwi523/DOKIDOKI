package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.request.CommentRequest;
import com.dokidoki.auction.dto.response.CommentResponse;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{auction_id}")
    public ResponseEntity<CommonResponse<List<CommentResponse>>> readComment(@PathVariable Long auction_id) {
        List<CommentResponse> comments = commentService.readComment(auction_id);
        // 댓글이 없을 경우 204 (No Content) 반환
        return new ResponseEntity<>(
                CommonResponse.of(comments.size() != 0 ? 200 : 204, "성공", comments),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse<Void>> createComment(Optional<CommentRequest> commentRequest) {
        // Request Body가 없을 경우,
        if (commentRequest.isEmpty()) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        CommentRequest comment = commentRequest.get();

        // 경매 식별번호가 없을 경우,
        if (comment.getAuction_id() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "경매 식별번호가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }
        // 사용자 식별번호가 없을 경우,
        if (comment.getMember_id() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "사용자 식별번호가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }
        // 댓글이 빈 문자열일 경우,
        if (comment.getContent() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "댓글 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 댓글 등록 및 결과 반환
        int resultCode = commentService.createComment(comment);

        // 유효성 검증, 오류가 존재하면 오류 메시지가 포함된 Response 객체 반환
        ResponseEntity<CommonResponse<Void>> errorResponse = checkResultCode(resultCode);
        if (errorResponse != null) {
            return errorResponse;
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, "댓글이 등록되었습니다.", null),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Void>> updateComment(@PathVariable Long comment_id, Optional<CommentRequest> commentRequest) {
        // Request Body가 없거나 없는 댓글, 또는 비어있는 댓글일 경우,
        if (commentRequest.isEmpty() || commentRequest.get().getContent() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 업데이트
        int resultCode = commentService.updateComment(comment_id, commentRequest.get());

        // 유효성 검증, 오류가 존재하면 오류 메시지가 포함된 Response 객체 반환
        ResponseEntity<CommonResponse<Void>> errorResponse = checkResultCode(resultCode);
        if (errorResponse != null) {
            return errorResponse;
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, "댓글이 수정되었습니다.", null),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(@PathVariable Long comment_id) {
        int resultCode = commentService.deleteComment(comment_id);

        // 유효성 검증, 오류가 존재하면 오류 메시지가 포함된 Response 객체 반환
        ResponseEntity<CommonResponse<Void>> errorResponse = checkResultCode(resultCode);
        if (errorResponse != null) {
            return errorResponse;
        }

        return new ResponseEntity<>(
                CommonResponse.of(200, "댓글이 삭제되었습니다.", null),
                HttpStatus.OK
        );
    }

    private ResponseEntity<CommonResponse<Void>> checkResultCode(int resultCode) {
        // 댓글 관련 오류코드
        final int NO_AUCTION = 1;  // 경매 식별번호 없을 경우
        final int NO_MEMBER = 2;  // 사용자 식별번호 없을 경우
        final int BLANK_CONTENT = 3;  // 공백 문자열일 경우
        final int MAX_LENGTH_EXCEEDED = 4;  // 최대 길이를 초과할 경우
        final int NO_COMMENT = 5;  // 부모 댓글 식별번호가 없을 경우

        switch (resultCode) {
            case NO_AUCTION:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "존재하지 않는 경매입니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case NO_MEMBER:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "존재하지 않는 사용자입니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case BLANK_CONTENT:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "댓글의 내용이 없습니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case MAX_LENGTH_EXCEEDED:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "최대 길이를 초과하였습니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case NO_COMMENT:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "존재하지 않는 댓글입니다.", null),
                        HttpStatus.BAD_REQUEST
                );
        }
        return null;
    }
}
