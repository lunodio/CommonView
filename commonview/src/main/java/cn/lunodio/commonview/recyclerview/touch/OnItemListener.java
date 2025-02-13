package cn.lunodio.commonview.recyclerview.touch;

public interface OnItemListener {
    void onMove(int startPosition,int endPosition);

    void onStop();

    void onStart();
}
