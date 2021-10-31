package ir.bahonar.azmooneh.domain.answer;

import java.io.File;

import ir.bahonar.azmooneh.domain.exam.Exam;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;

public class FileAnswer extends Answer{

    //field
    private final File answer;

    //constructor
    public FileAnswer(String id, Question question, User student, File answer,AnswerExam answerExam) {
        super(id, question, student,answerExam);
        this.answer = answer;
    }

    //getter
    public File getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getType() {
        return AnswerType.file;
    }
}
