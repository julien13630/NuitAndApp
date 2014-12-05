package imhuman.app.com.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import imhuman.app.com.R;

public class ActionsAdapter extends ArrayAdapter<Actions> {

    Context context;
    int layoutResourceId;
    Actions data[] = null;

    public ActionsAdapter(Context context, int layoutResourceId, Actions[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ActionsHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ActionsHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (ActionsHolder)row.getTag();
        }

        Actions actions = data[position];
        holder.txtTitle.setText(actions.title);
        holder.imgIcon.setImageResource(actions.icon);

        return row;
    }

    static class ActionsHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
