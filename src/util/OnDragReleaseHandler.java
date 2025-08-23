package util;

@FunctionalInterface
public interface OnDragReleaseHandler {
    void onRelease(int oldX, int oldY);
}
