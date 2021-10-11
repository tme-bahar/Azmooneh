package ir.bahonar.azmooneh.domain.exam.ExamStatus.ForTeacher;

import ir.bahonar.azmooneh.domain.answer.Answer;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class ExamStatusForTeacher {
    //field
    private final String id;
    private final Exam exam;
    public enum ExamState{notStarted,running,finished}
    public enum data{startingTime,finishingTime,Long,numberOfUnderHalf}
    //constructor
    public ExamStatusForTeacher(String id,Exam exam){
        this.id = id;
        this.exam = exam;
    }

    //getters
    public String getId() {
        return id;
    }

    public Exam getExam() {
        return exam;
    }

    //get data
    public int numberOfQuestions(){return exam.getQuestions().size();}
    public int numberOfStudents(){return exam.getStudents().size();}
    public float average(){
        return exam.getStudentAnswers().getAverage();
    }
    public float minGrade(){
        return exam.getStudentAnswers().getMinGrade();
    }
    public float maxGrade(){
        return exam.getStudentAnswers().getMaxGrade();
    }
    public int numberOfUnder(int i){return exam.getStudentAnswers().numberOfUnder(i);}

}
