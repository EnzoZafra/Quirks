package cmput301f17t12.quirks.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f17t12.quirks.Models.Drop;
import cmput301f17t12.quirks.R;

public class CollectibleItemAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Drop> list = new ArrayList<>();
    private Context context;

    public CollectibleItemAdapter(ArrayList<Drop> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * Return the size of the Drop List
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Return item at the specified index
     * @param pos Index
     * @return Item object
     */
    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    /**
     * Displays the data at specified indexes within the ListView
     * @param pos Position of the item selected
     * @param convertView The old view that can be reused
     * @param viewGroup The parent ListView
     * @return A View representing the data at the corresponding position
     */
    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.collectible_item, null);
        }

        final Drop item = list.get(pos);

        // Handle checkboxes
        final CheckBox collectibleBox = view.findViewById(R.id.collectiblebox);
        collectibleBox.setFocusable(false);
        collectibleBox.setFocusableInTouchMode(false);

        collectibleBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelected(isChecked);
            }
        });
        collectibleBox.setChecked(item.isSelected());

        //Handle TextView and display collectibles
        TextView collectibleName = view.findViewById(R.id.collectiblename);
        collectibleName.setText(item.getDropType().getName());

        switch (item.getDropType().getType()) {
            case "COMMON":
                collectibleName.setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.commondrop));
                break;
            case "UNCOMMON":
                collectibleName.setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.uncommondrop));
                break;
            case "RARE":
                collectibleName.setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.raredrop));
                break;
            case "MYTHICAL":
                collectibleName.setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.mythicaldrop));
                break;
            case "LEGENDARY":
                collectibleName.setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.legendarydrop));
                break;
            case "ANCIENT":
                collectibleName.setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.ancientdrop));
                break;
        }
        return view;
    }
}
