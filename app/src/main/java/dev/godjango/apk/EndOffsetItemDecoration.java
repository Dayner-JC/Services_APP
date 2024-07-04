package dev.godjango.apk;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EndOffsetItemDecoration extends RecyclerView.ItemDecoration {
    private final int endOffset;

    public EndOffsetItemDecoration(int endOffset) {
        this.endOffset = endOffset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            outRect.right = this.endOffset;
        }
    }
}
