package com.heednow.bo;


import com.heednow.request.question.OptionsList;

import java.util.List;

/**
 * Created by System1 on 9/21/2016.
 */
public class QuestionRequestBO {
    private String questionDesc;
    private char questionType;
    private int parentAnswerId;
    private int parentQuestionId;
    private int answerSymbol;
    public List<OptionsList> answerOption;
    private int clientId;

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

    public List<OptionsList> getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(List<OptionsList> answerOption) {
        this.answerOption = answerOption;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionRequestBO that = (QuestionRequestBO) o;

        if (questionType != that.questionType) return false;
        if (parentAnswerId != that.parentAnswerId) return false;
        if (parentQuestionId != that.parentQuestionId) return false;
        if (answerSymbol != that.answerSymbol) return false;
        if (clientId != that.clientId) return false;
        if (!questionDesc.equals(that.questionDesc)) return false;
        return answerOption.equals(that.answerOption);
    }

    @Override
    public int hashCode() {
        int result = questionDesc.hashCode();
        result = 31 * result + (int) questionType;
        result = 31 * result + parentAnswerId;
        result = 31 * result + parentQuestionId;
        result = 31 * result + answerSymbol;
        result = 31 * result + answerOption.hashCode();
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "QuestionRequestBO{" +
                "questionDesc='" + questionDesc + '\'' +
                ", questionType=" + questionType +
                ", parentAnswerId=" + parentAnswerId +
                ", parentQuestionId=" + parentQuestionId +
                ", answerSymbol=" + answerSymbol +
                ", answerOption=" + answerOption +
                ", clientId=" + clientId +
                '}';
    }
}
