package ricard.projecte.gestiogimnasandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

class ActivitatsAdapter extends RecyclerView.Adapter<ActivitatsAdapter.ViewHolder> {

    private ArrayList<Recycler> mSportsData;
    private Context mContext;


    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param sportsData ArrayList containing the sports data.
     * @param context    Context of the application.
     */
    ActivitatsAdapter(Context context, ArrayList<Recycler> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added
     *                 after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public ActivitatsAdapter.ViewHolder onCreateViewHolder( //viewHolder es cada element del recyclerview.
                                                        ViewGroup parent, int viewType) { //el viewgroup es el parametre que sera el linearlayout.
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitatsAdapter.ViewHolder holder, int position) {
        Recycler recycler = mSportsData.get(position);
        holder.bindTo(recycler);
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */


    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //clase interna

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportsImage; //creem variable de tipus ImageView.

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mSportsImage = itemView.findViewById(R.id.sportsImage); //inicialitzem el atribut ImageView.

            itemView.setOnClickListener(this);
        }

        void bindTo(Recycler currentSport) {
            // Populate the textviews with data.
            mTitleText.setText(currentSport.getNom()); //omplim els textview amb el esport.
            mInfoText.setText(currentSport.getDescripcio());
            Glide.with(mContext).load(currentSport.getImageId()).into(mSportsImage);

        }

        @Override
        public void onClick(View view) {
            Recycler currentSport = mSportsData.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentSport.getNom());
            detailIntent.putExtra("image_resource", currentSport.getImageId());
            mContext.startActivity(detailIntent);
        }
    }
}

