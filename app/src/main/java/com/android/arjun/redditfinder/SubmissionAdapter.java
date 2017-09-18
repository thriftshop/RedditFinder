package com.android.arjun.redditfinder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.jreddit.entity.Submission;

import java.util.List;

import static com.android.arjun.redditfinder.Generics.getNotNullValue;


public class SubmissionAdapter extends ArrayAdapter<Submission> {
    @NonNull
    private final Context context;

    public SubmissionAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Submission> submissions) {
        super(context, resource, textViewResourceId, submissions);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Submission submission = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.submission_row, parent, false);

        if (submission == null) return convertView;

        final Long score = submission.getScore();
        final String question = submission.getTitle();
        final String author = submission.getAuthor();

        final TextView questionTextView = convertView.findViewById(R.id.question);
        final TextView authorTextView = convertView.findViewById(R.id.author);

        questionTextView.setText(getNotNullValue(question, ""));
        authorTextView.setText(getNotNullValue(author, ""));
        ((TextView) convertView.findViewById(R.id.score)).setText(getNotNullValue(score.toString(), "0"));

        View.OnClickListener openWebPage = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webPage = new Intent(context, WebPageActivity.class);
                webPage.putExtra("url", submission.getURL());
                context.startActivity(webPage);
            }
        };

        questionTextView.setOnClickListener(openWebPage);
        authorTextView.setOnClickListener(openWebPage);

        return convertView;
    }
}
