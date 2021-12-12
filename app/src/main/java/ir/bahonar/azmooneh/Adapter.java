package ir.bahonar.azmooneh;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<Exam> localDataSet;
    private OnItemClickListener onItemClickListener = (e,p)->{};
    private OnItemLongClickListener onItemLongClickListener =(e,p)->{};
    public interface OnItemClickListener {
        void onItemClick(Exam exam,int position);
    }
    public interface OnItemLongClickListener {
        void onItemClick(Exam exam,int position);
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateAndTime;
        public final TextView teacher;
        public final TextView status;
        public final TextView name;
        public final ConstraintLayout cons;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            dateAndTime = (TextView) view.findViewById(R.id.textView4);
            teacher = (TextView) view.findViewById(R.id.textView7);
            status = (TextView) view.findViewById(R.id.textView15);
            name = (TextView) view.findViewById(R.id.textView17);
            cons = view.findViewById(R.id.cons);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public Adapter(List<Exam> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        String[] dt = localDataSet.get(position).getStartingTime().split("&");
        viewHolder.dateAndTime.setText(dt[0]+"\n"+dt[1]);
        viewHolder.teacher.setText(localDataSet.get(position).getTeacher().getFirstName() + " " +localDataSet.get(position).getTeacher().getLastName());
        //TODO Status must complete
        Exam.Status stat = localDataSet.get(position).getStatus();
        viewHolder.status.setText(stat == Exam.Status.running ?
                ActivityHolder.activity.getResources().getString(R.string.running):(stat == Exam.Status.notStarted ?
                ActivityHolder.activity.getResources().getString(R.string.not_started):
                ActivityHolder.activity.getResources().getString(R.string.finished)));
        viewHolder.cons.setBackgroundColor(Color.parseColor(stat == Exam.Status.running ?
                "#006600":(stat == Exam.Status.notStarted ?"#000066":"#660000")));
        viewHolder.name.setText(localDataSet.get(position).getName());
        viewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(localDataSet.get(position),position));
        viewHolder.itemView.setOnLongClickListener(v -> {
            onItemLongClickListener.onItemClick(localDataSet.get(position),position);
            return true;
        });

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}