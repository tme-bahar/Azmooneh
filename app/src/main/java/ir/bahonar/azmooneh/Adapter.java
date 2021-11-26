package ir.bahonar.azmooneh;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<Exam> localDataSet;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
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

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            dateAndTime = (TextView) view.findViewById(R.id.textView4);
            teacher = (TextView) view.findViewById(R.id.textView7);
            status = (TextView) view.findViewById(R.id.textView15);
            name = (TextView) view.findViewById(R.id.textView17);
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
        viewHolder.dateAndTime.setText(localDataSet.get(position).getStartingTime());
        viewHolder.teacher.setText(localDataSet.get(position).getTeacher().getFirstName() + " " +localDataSet.get(position).getTeacher().getLastName());
        //TODO Status must complete
        viewHolder.status.setText(localDataSet.get(position).getStatus() == Exam.Status.running ?
                ActivityHolder.activity.getResources().getString(R.string.running):"");
        viewHolder.name.setText(localDataSet.get(position).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(localDataSet.get(position),position);
            }
        });

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}