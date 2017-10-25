package com.kisilevskiy.config;

public class ApplicationConstants {

    private ApplicationConstants() {  }

    public static class LoggerTexts {
        private LoggerTexts() {  }

        public static final String LINEAR_ELAPSED_TIME = "Linear elapsed time: ";
        public static final String PARALLEL_ELAPSED_TIME = "Parallel elapsed time: ";
    }

    public static class ExceptionMessages {
        private ExceptionMessages() {  }

        public static final String WRONG_AMOUNT_OF_ROWS = "Amount of rows should be greater then 0";
        public static final String WRONG_AMOUNT_OF_COLUMNS = "Amount of columns should be greater then 0";
        public static final String WRONG_MATRICES_SIZES = "First matrix column size should be equal to second matrix row size";
    }

}