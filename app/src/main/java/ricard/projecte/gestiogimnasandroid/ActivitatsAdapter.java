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

    private ArrayList<RecyclerActivitats> mActivitatsData;
    private Context mContext;
    public Client client;
    public ArrayList<Activitat> disponibles;
    public ArrayList<Activitat> inscrites;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param sportsData ArrayList containing the sports data.
     * @param context    Context of the application.
     */
    ActivitatsAdapter(Context context, ArrayList<RecyclerActivitats> sportsData, Client client, ArrayList<Activitat>disponibles, ArrayList<Activitat>inscrites) {
        this.mActivitatsData = sportsData;
        this.mContext = context;
        this.client = client;
        this.disponibles=disponibles;
        this.inscrites=inscrites;
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
                inflate(R.layout.list_item_activitat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitatsAdapter.ViewHolder holder, int position) {
        RecyclerActivitats recyclerActivitats = mActivitatsData.get(position);
        holder.bindTo(recyclerActivitats);
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
        return mActivitatsData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //clase interna

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mActivitatsImage; //creem variable de tipus ImageView.

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item_activitat_activitat.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mActivitatsImage = itemView.findViewById(R.id.sportsImage); //inicialitzem el atribut ImageView.

            itemView.setOnClickListener(this);
        }

        void bindTo(RecyclerActivitats currentSport) {
            // Populate the textviews with data.
            mTitleText.setText(currentSport.getNom()); //omplim els textview amb el esport.
            mInfoText.setText(currentSport.getDescripcio());
            Glide.with(mContext).load(currentSport.getImageId()).into(mActivitatsImage);

        }

        //Al fer click sobre l'activitat, obtenim el nom del recurs i enviem un String amb el nom de l'activitat a la DetailActivity.
        @Override
        public void onClick(View view) {
            String nom ="";
            RecyclerActivitats currentSport = mActivitatsData.get(getAdapterPosition());
            nom = currentSport.getNom();

            switch(nom){
                case "res/drawable/jiujitsu.jpg":
                   String act1 = "Jiu Jitsu";
                    Intent detailIntent1 = new Intent(mContext, DetailActivity.class);
                    detailIntent1.putExtra("NomActivitat", act1);
                    detailIntent1.putExtra("Client", client);
                    detailIntent1.putExtra("Disponibles", disponibles);
                    detailIntent1.putExtra("Inscrites", inscrites);
                    mContext.startActivity(detailIntent1);
                    break;
                case "res/drawable/karate.jpg":
                    String act2 = "Karate";
                    Intent detailIntent2 = new Intent(mContext, DetailActivity.class);
                    detailIntent2.putExtra("NomActivitat", act2);
                    detailIntent2.putExtra("Client", client);
                    detailIntent2.putExtra("Disponibles", disponibles);
                    detailIntent2.putExtra("Inscrites", inscrites);
                    mContext.startActivity(detailIntent2);
                    break;
                case "res/drawable/kickboxing.jpg":
                    String act3 = "KickBoxing";
                    Intent detailIntent3 = new Intent(mContext, DetailActivity.class);
                    detailIntent3.putExtra("NomActivitat", act3);
                    detailIntent3.putExtra("Client", client);
                    detailIntent3.putExtra("Disponibles", disponibles);
                    detailIntent3.putExtra("Inscrites", inscrites);
                    mContext.startActivity(detailIntent3);
                    break;
                case "res/drawable/sambo.jpg":
                    String act4 = "Sambo";
                    Intent detailIntent4 = new Intent(mContext, DetailActivity.class);
                    detailIntent4.putExtra("NomActivitat", act4);
                    detailIntent4.putExtra("Client", client);
                    detailIntent4.putExtra("Disponibles", disponibles);
                    detailIntent4.putExtra("Inscrites", inscrites);
                    mContext.startActivity(detailIntent4);
                    break;
            }
        }
    }
}

