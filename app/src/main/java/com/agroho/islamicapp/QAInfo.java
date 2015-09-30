package com.agroho.islamicapp;

/**
 * Created by Rezaul on 2015-09-29.
 */
public class QAInfo {

    private String qaId;
    private String qaTitle;
    private String qaQuestion;
    private String qaAnswer;
    private String category;

    public QAInfo() {
    }

    public QAInfo(String id, String qaTitle, String qaQuestion, String qaAnswer, String category) {
        this.qaId = id;
        this.qaTitle = qaTitle;
        this.qaQuestion = qaQuestion;
        this.qaAnswer = qaAnswer;
        this.category = category;
    }

    public String getId() {
        return qaId;
    }

    public void setId(String id) {
        this.qaId = id;
    }

    public String getqaTitle() {
        return qaTitle;
    }

    public void setqaTitle(String qaTitle) {
        this.qaTitle = qaTitle;
    }

    public String getqaQuestion() {
        return qaQuestion;
    }

    public void setqaQuestion(String qaQuestion) {
        this.qaQuestion = qaQuestion;
    }

    public String getqaAnswer() {
        return qaAnswer;
    }

    public void setqaAnswer(String qaAnswer) {
        this.qaAnswer = qaAnswer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "QAInfo{" +
                "id=" + qaId +
                ", uniName='" + qaTitle + '\'' +
                ", UniNotice='" + qaQuestion + '\'' +
                ", DetailsUrl='" + qaAnswer + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
