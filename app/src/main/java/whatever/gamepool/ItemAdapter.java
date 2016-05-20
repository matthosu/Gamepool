package whatever.gamepool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter {
    ItemContainer container = ItemContainer.getInstance();

    public ItemAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler {
        ImageView icon;
        TextView title;
        TextView details;
    }

    @Override
    public int getCount() {
        return container.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DataHandler handler;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_layout, parent, false);
            handler = new DataHandler();
            handler.icon = (ImageView) row.findViewById(R.id.iconComponent);
            handler.title = (TextView) row.findViewById(R.id.titleComponent);
            handler.details = (TextView) row.findViewById(R.id.desComponent);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }

        handler.icon.setImageResource(container.getActivityIcon(position));
        String description[] = container.getActivityDescription(position);
        handler.title.setText(description[0]);
        handler.details.setText(description[1]);

        return row;
    }
}
