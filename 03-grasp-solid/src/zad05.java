//Naruszenia ISP w bibliotece standardowej Javy:

package java.awt;
public interface Adjustable {
    int HORIZONTAL = 0;
    int VERTICAL = 1;
    int NO_ORIENTATION = 2;

    int getOrientation();
    void setMinimum(int i);
    int getMinimum();
    void setMaximum(int i);
    int getMaximum();
    void setUnitIncrement(int i);
    int getUnitIncrement();
    void setBlockIncrement(int i);
    int getBlockIncrement();
    void setVisibleAmount(int i);
    int getVisibleAmount();
    void setValue(int i);
    int getValue();
    void addAdjustmentListener(java.awt.event.AdjustmentListener adjustmentListener);
    void removeAdjustmentListener(java.awt.event.AdjustmentListener adjustmentListener);
}

/*
Powyższy interfejs wymaga połączenia conajmniej kilku odpowiedzialności:
 - pobranie orientacji (getOrientation)
 - pobierania i ustawiania minimum/maximum
 - innych rzeczy UnitIncrement, BlockIncrement, VisibleAmount
 - Value?
 - dodawania i usuwania adjustment listenerów

Jak nakazuje ISP, można by go podzielić na kilka mniejszych interfejsów
 */

package java.awt.image;

public interface WritableRenderedImage extends java.awt.image.RenderedImage {
    void addTileObserver(java.awt.image.TileObserver tileObserver);
    void removeTileObserver(java.awt.image.TileObserver tileObserver);
    java.awt.image.WritableRaster getWritableTile(int i, int i1);
    void releaseWritableTile(int i, int i1);
    boolean isTileWritable(int i, int i1);
    java.awt.Point[] getWritableTileIndices();
    boolean hasTileWriters();
    void setData(java.awt.image.Raster raster);
}

