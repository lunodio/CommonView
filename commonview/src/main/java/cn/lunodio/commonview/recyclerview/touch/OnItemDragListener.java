package cn.lunodio.commonview.recyclerview.touch;


public interface OnItemDragListener {

    void onItemMove(int startPos, int endPos);

    void onItemMoveState(int state);

    void onItemRemoveState(int state);

    void onItemRemove(int position);
}
