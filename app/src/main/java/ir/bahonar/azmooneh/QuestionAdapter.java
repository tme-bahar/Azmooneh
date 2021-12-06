package ir.bahonar.azmooneh;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.bahonar.azmooneh.Activities.QuestionList;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final List<Question> localDataSet;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(Question question, int position);
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView type;
        public final TextView number;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            type = (TextView) view.findViewById(R.id.textView28);
            number = (TextView) view.findViewById(R.id.textView19);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public QuestionAdapter(List<Question> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.questionlist, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        try{
        String type = ActivityHolder.activity.getResources().getString(localDataSet.get(position).getChoices() < 2 ? R.string._4_choice : R.string.text);
        viewHolder.number.setText(localDataSet.get(position).getNumber());
        viewHolder.type.setText(type);
        //TODO Status must complete
        viewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(localDataSet.get(position),position));}
        catch (Exception e) {
            e.printStackTrace();
        }

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