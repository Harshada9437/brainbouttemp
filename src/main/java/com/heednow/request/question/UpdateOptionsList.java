package com.heednow.request.question;

/**
 * Created by System-2 on 12/24/2016.
 */
public class UpdateOptionsList {
    private int answer_id;
        private String answerDesc;
    private String threshold;
    private int rating;
    private int weightage;

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    @Override
    public String toString() {
        return "UpdateOptionsList{" +
                "answer_id=" + answer_id +
                ", answerDesc='" + answerDesc + '\'' +
                ", threshold='" + threshold + '\'' +
                ", rating=" + rating +
                ", weightage=" + weightage +
                '}';
    }
}
