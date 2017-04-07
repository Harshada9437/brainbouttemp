package com.heednow.response.Answer;

/**
 * Created by System-2 on 12/19/2016.
 */
public class AnswerResponseList
{
    private String answerDesc;
    private int rating;
    private int weightage;
    private int answer_id;
    private String threshold;

    public AnswerResponseList(String answerDesc, int rating, int answer_id,int weightage, String threshold) {
        this.answerDesc = answerDesc;
        this.rating = rating;
        this.answer_id = answer_id;
        this.weightage = weightage;
        this.threshold = threshold;

    }

    public String getThreshold() {
        return threshold;
    }

    public int getWeightage() {
        return weightage;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerResponseList that = (AnswerResponseList) o;

        if (rating != that.rating) return false;
        if (weightage != that.weightage) return false;
        if (answer_id != that.answer_id) return false;
        if (answerDesc != null ? !answerDesc.equals(that.answerDesc) : that.answerDesc != null) return false;
        return threshold != null ? threshold.equals(that.threshold) : that.threshold == null;
    }

    @Override
    public int hashCode() {
        int result = answerDesc != null ? answerDesc.hashCode() : 0;
        result = 31 * result + rating;
        result = 31 * result + weightage;
        result = 31 * result + answer_id;
        result = 31 * result + (threshold != null ? threshold.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnswerResponseList{" +
                "answerDesc='" + answerDesc + '\'' +
                ", rating=" + rating +
                ", weightage=" + weightage +
                ", answer_id=" + answer_id +
                ", threshold='" + threshold + '\'' +
                '}';
    }
}
