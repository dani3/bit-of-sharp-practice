package engine.renderer.buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BufferLayout implements Iterable<BufferElement> {

    private List<BufferElement> mElements;
    private int mStride;

    public BufferLayout() {
    }

    public BufferLayout(Collection<BufferElement> c) {
        mElements = new ArrayList<>(c);
        calculateOffsetsAndStride();
    }

    private void calculateOffsetsAndStride() {
        int offset = 0;
        mStride = 0;
        for (var e : mElements) {
            e.mOffset = offset;
            offset += e.mSize;
            mStride += e.mSize;
        }
    }

    public int getStride() {
        return mStride;
    }

    public List<BufferElement> getElements() {
        return mElements;
    }

    @Override
    public Iterator<BufferElement> iterator() {
        return new BufferLayoutIterator(this);
    }

    private class BufferLayoutIterator implements Iterator<BufferElement> {

        private Iterator<BufferElement> mIterator;

        public BufferLayoutIterator(BufferLayout layout) {
            mIterator = layout.mElements.iterator();
        }

        @Override
        public boolean hasNext() {
            return mIterator.hasNext();
        }

        @Override
        public BufferElement next() {
            return mIterator.next();
        }

        @Override
        public void remove() {
            mIterator.remove();
        }
    }
}
