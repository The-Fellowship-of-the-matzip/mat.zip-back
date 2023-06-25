package com.woowacourse.matzip.exception;

public class UploadFailedException extends RuntimeException{
    public UploadFailedException() {
        super("업로드에 실패했습니다.");
    }
}
