package engine.core;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of a stack of layers that will determine the draw order
 * and the event handling.
 */
public class LayerStack implements Iterable<Layer> {

    private final List<Layer> mLayers;

    /**
     * Construct a LayerStack.
     */
    public LayerStack() {
        mLayers = new ArrayList<>();
    }

    /**
     * Add a new layer to the front of the stack.
     *
     * @param layer layer to be pushed to the stack.
     */
    public void pushLayer(Layer layer) {
        mLayers.add(0, layer);
    }

    /**
     * Add a new layer to the back of the stack.
     *
     * @param overlay layer to be pushed to the back of the stack.
     */
    public void pushOverlay(Layer overlay) {
        mLayers.add(overlay);
    }

    /**
     * Remove the given layer from the stack.
     *
     * @param layer layer to be removed.
     */
    public void popLayer(Layer layer) {
        mLayers.remove(layer);
    }

    /**
     * Remove the given layer from the stack.
     *
     * @param overlay layer to be removed.
     */
    public void popOverlay(Layer overlay) {
        mLayers.remove(overlay);
    }

    /**
     * Returns an iterator over elements of type {@code Layer}.
     *
     * @return an Iterator.
     */
    @Override
    public @NotNull Iterator<Layer> iterator() {
        return new LayerStackIterator(this);
    }

    /**
     * LayerStack iterator implementation.
     */
    private class LayerStackIterator implements Iterator<Layer> {

        private Iterator<Layer> mIterator;

        private LayerStackIterator(LayerStack stack) {
            mIterator = stack.mLayers.iterator();
        }

        @Override
        public boolean hasNext() {
            return mIterator.hasNext();
        }

        @Override
        public Layer next() {
            return mIterator.next();
        }

        @Override
        public void remove() {
            mIterator.remove();
        }
    }
}
