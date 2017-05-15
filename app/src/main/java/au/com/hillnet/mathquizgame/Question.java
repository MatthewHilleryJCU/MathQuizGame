package au.com.hillnet.mathquizgame;


class Question {
    private int num1;
    private int num2;
    private char oper;
    private int ans;
    //question number
    private int count;
    private String input;
    private QStatus status;

    Question(int num1, int num2, char oper, int ans, int count) {
        this.num1 = num1;
        this.num2 = num2;
        this.oper = oper;
        this.ans = ans;
        this.count = count;
        this.input = "";
        this.status = QStatus.Unanswered;
    }


    int getAns() {
        return this.ans;
    }

    String getInput() {
        return this.input;
    }

    QStatus getStatus() {
        return this.status;
    }

    String getLine() {
        return String.format("%d)  %d %c %d = ", count, num1, oper, num2);
    }

    void setInput(String s) {
        this.input = s;
    }

    void setStatus(QStatus s) {
        this.status = s;
    }
}
