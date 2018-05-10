package cmput301f17t12.quirks.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f17t12.quirks.Activities.EventListActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Activities.RequestActivity;
import cmput301f17t12.quirks.Models.Drop;
import cmput301f17t12.quirks.Models.Request;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class RequestListItemAdapter extends BaseAdapter implements ListAdapter {

    Dialog myDialog;
    ArrayList<Request> requests = new ArrayList<Request>();
    private Context context;

    public RequestListItemAdapter(ArrayList<Request> requests, Context context){
        this.requests = requests;
        this.context = context;
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int i) {
        return requests.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {


        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.request_item, null);
        }

        final Request request = requests.get(i);
        TextView requestTxt = (TextView) view.findViewById(R.id.requestTxt);
        requestTxt.setText(request.getHeader());

        ImageButton buttonAccept = (ImageButton) view.findViewById(R.id.buttonAccept);
        ImageButton buttonDecline = (ImageButton) view.findViewById(R.id.buttonDecline);
        Button tradeDetail = (Button) view.findViewById(R.id.tradeDetail);
        ImageView imgview = (ImageView) view.findViewById(R.id.imgview);
        TextView detailTxt = (TextView) view.findViewById(R.id.detailTxt);

        if (request.getDetails() == null) {
            tradeDetail.setVisibility(View.VISIBLE);
            imgview.setImageResource(R.drawable.ic_compare_arrows_black_24dp);
            detailTxt.setText("");
            TradeDetailItemAdapter giveAdapter;
            TradeDetailItemAdapter receiveAdapter;

            if (context instanceof RequestActivity) {
                final ArrayList<Drop> give = ((RequestActivity)context).getGiveInfo(i);
                final ArrayList<Drop> receive = ((RequestActivity)context).getReceiveInfo(i);

                tradeDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog = new Dialog(view.getContext());

                        myDialog.setContentView(R.layout.trade_detail);
                        TextView detailclose =(TextView) myDialog.findViewById(R.id.detailclose);
                        ListView givelist = (ListView) myDialog.findViewById(R.id.givelist);
                        ListView receivelist = (ListView) myDialog.findViewById(R.id.receivelist);

                        TradeDetailItemAdapter giveAdapter = new TradeDetailItemAdapter(give, context);
                        TradeDetailItemAdapter receiveAdapter = new TradeDetailItemAdapter(receive, context);

                        givelist.setAdapter(giveAdapter);
                        receivelist.setAdapter(receiveAdapter);

                        detailclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.dismiss();
                            }
                        });

                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        myDialog.show();
                    }
                });
            }

        } else {
            tradeDetail.setVisibility(View.INVISIBLE);
            imgview.setImageResource(R.drawable.ic_social);
            detailTxt.setText(request.getDetails());
        }

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof RequestActivity){
                    ((RequestActivity)context).acceptRequest(i);
                }
            }
        });

        buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof RequestActivity){
                    ((RequestActivity)context).declineRequest(i);
                }
            }
        });

        return view;
    }
}
