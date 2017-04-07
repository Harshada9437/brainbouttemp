package com.heednow.response.question;

/**
 * Created by System1 on 9/21/2016.
 */
public class QuestionResponse {
    private int id;
    private String parentAnswerDesc;
    private String parentQuestionDesc;
    private String questionDesc;
    private char questionType;
    private int parentAnswerId;
    private int parentQuestionId;
    private int answerSymbol;

    public String getParentAnswerDesc() {return parentAnswerDesc;}

    public void setParentAnswerDesc(String parentAnswerDesc) {this.parentAnswerDesc = parentAnswerDesc;}

    public String getParentQuestionDesc() {return parentQuestionDesc;}

    public void setParentQuestionDesc(String parentQuestionDesc) {this.parentQuestionDesc = parentQuestionDesc;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public char getQuestionType() {
        return questionType;
    }

    public void setQuestionType(char questionType) {
        this.questionType = questionType;
    }

    public int getParentAnswerId() {
        return parentAnswerId;
    }

    public void setParentAnswerId(int parentAnswerId) {
        this.parentAnswerId = parentAnswerId;
    }

    public int getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(int parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }

    public int getAnswerSymbol() {
        return answerSymbol;
    }

    public void setAnswerSymbol(int answerSymbol) {
        this.answerSymbol = answerSymbol;
    }

    @Override
    public String toString() {
        return "QuestionResponse{" +
                "id=" + id +
                ", parentAnswerDesc='" + parentAnswerDesc + '\'' +
                ", parentQuestionDesc='" + parentQuestionDesc + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", questionType=" + questionType +
                ", parentAnswerId=" + parentAnswerId +
                ", parentQuestionId=" + parentQuestionId +
                ", answerSymbol=" + answerSymbol +
                '}';
    }
}
