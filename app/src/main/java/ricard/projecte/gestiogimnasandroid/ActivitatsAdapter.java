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

    ActivitatsAdapter(Context context, ArrayList<RecyclerActivitats> sportsData, Client client, ArrayList<Activitat>disponibles, ArrayList<Activitat>inscrites) {
        this.mActivitatsData = sportsData;
        this.mContext = context;
        this.client = client;
        this.disponibles=disponibles;
        this.inscrites=inscrites;
    }

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

    @Override
    public int getItemCount() {
        return mActivitatsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //clase interna

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mActivitatsImage; //creem variable de tipus ImageView.

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mActivitatsImage = itemView.findViewById(R.id.sportsImage); //inicialitzem el atribut ImageView.

            itemView.setOnClickListener(this);
        }

        void bindTo(RecyclerActivitats currentAct) {
            // Populate the textviews with data.
            mTitleText.setText(currentAct.getNom()); //omplim els textview amb el esport.
            mInfoText.setText(currentAct.getDescripcio());
            Glide.with(mContext).load(currentAct.getImageId()).into(mActivitatsImage);

        }

        //Al fer click sobre l'activitat, obtenim el nom del recurs i enviem un String amb el nom de l'activitat a la DetailActivitats.
        @Override
        public void onClick(View view) {
            String nom ="";
            RecyclerActivitats currentAct = mActivitatsData.get(getAdapterPosition());
            nom = currentAct.getNom();

            switch(nom){
                case "res/drawable/jiujitsu.jpg":
                   String act1 = "Jiu Jitsu";
                    Intent detailIntent1 = new Intent(mContext, DetailActivitats.class);
                    detailIntent1.putExtra("NomActivitat", act1);
                    detailIntent1.putExtra("Client", client);
                    detailIntent1.putExtra("Disponibles", disponibles);
                    detailIntent1.putExtra("Inscrites", inscrites);
                    mContext.startActivity(detailIntent1);
                    break;
                case "res/drawable/karate.jpg":
                    String act2 = "Karate";
                    Intent detailIntent2 = new Intent(mContext, DetailActivitats.class);
                    detailIntent2.putExtra("NomActivitat", act2);
                    detailIntent2.putExtra("Client", client);
                    detailIntent2.putExtra("Disponibles", disponibles);
                    detailIntent2.putExtra("Inscrites", inscrites);
                    mContext.startActivity(detailIntent2);
                    break;
                case "res/drawable/kickboxing.jpg":
                    String act3 = "KickBoxing";
                    Intent detailIntent3 = new Intent(mContext, DetailActivitats.class);
                    detailIntent3.putExtra("NomActivitat", act3);
                    detailIntent3.putExtra("Client", client);
                    detailIntent3.putExtra("Disponibles", disponibles);
                    detailIntent3.putExtra("Inscrites", inscrites);
                    mContext.startActivity(detailIntent3);
                    break;
                case "res/drawable/sambo.jpg":
                    String act4 = "Sambo";
                    Intent detailIntent4 = new Intent(mContext, DetailActivitats.class);
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

