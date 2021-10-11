package ir.bahonar.azmooneh.domain.exam;

import java.util.ArrayList;
import java.util.List;

import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.answer.Answer;
import ir.bahonar.azmooneh.domain.answer.CorrectAnswer;
import ir.bahonar.azmooneh.domain.answer.MultiChoiceAnswer;


public class CorrectExam {
    //fields
    private final String id;
    private final Exam exam;
    /** value -1 is blank **/
    private final List<CorrectAnswer> answers;

    //constructor
    public CorrectExam(String id, Exam exam) {
        this.id = id;
        if (exam.isMultiQuestion())
            this.exam = exam;
        else
            this.exam = null;
        answers = new ArrayList<>();

    }

    //getters

    public String getId() {
        return id;
    }

    public Exam getExam() {
        return exam;
    }

    public List<CorrectAnswer> getAnswers() {
        return answers;
    }

    public CorrectAnswer getAnswer(int index) {
        return answers.get(index);
    }



    public CorrectAnswer getAnswer(Question question) {
        for (int i = 0; i < exam.getQuestions().size(); i++) {
            if (answers.get(i).getQuestion().equals(question))
                return answers.get(i);
        }
        return null;
    }

    //adders
    public boolean add(CorrectAnswer correctAnswer){
        if(getAnswer(correctAnswer.getQuestion()) != null)
            return false;
        answers.add(correctAnswer);
        return true;
    }

    //remover
    public void remove(Question question){
        for (CorrectAnswer ca:answers)
            if(ca.getQuestion().equals(question))
                answers.remove(ca);
    }

    public boolean isComplete(){
        boolean result = (answers.size() == exam.getQuestions().size());
        for (CorrectAnswer ca :answers)
            result &= (ca.getAnswer()>1);
        return result;
    }


    //check answers
    private boolean check(MultiChoiceAnswer a){
        if(!isComplete())
            return false;
        for (CorrectAnswer ca:answers)
            if(ca.getQuestion().equals(a.getQuestion())) {
                if (a.getAnswer() == ca.getAnswer())
                    a.setGrade(a.getQuestion().getMaxGrade());
                return true;
            }

        return false;
    }
    public boolean check(MultiChoiceAnswer ... answers){
        boolean result = true;
        for (MultiChoiceAnswer a:answers)
            result &= check(a);
        return result;
    }
}
