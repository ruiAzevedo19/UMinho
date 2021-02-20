{-# LANGUAGE FlexibleContexts #-}

module BMP where

import qualified Data.Vector.Storable as V
import Codec.Picture.Types
import Codec.Picture
import Data.Matrix hiding (trace)
import Control.Monad

img2matrix :: Pixel a => Image a -> IO (Matrix a)
img2matrix i = return $! pixelFold go m i
    where
    m = matrix (imageHeight i) (imageWidth i) (\(j,i) -> error $ "img2matrix " ++ show i ++" "++ show j)
    go m x y p = unsafeSet p (y+1,x+1) m 

matrix2img :: Pixel a => Matrix a -> IO (Image a)
matrix2img xs = do
    mi <- newMutableImage (ncols xs) (nrows xs)
    foldMatrixM (\_ y x p -> unsafeWritePixel (mutableImageData mi) (mutablePixelBaseIndex mi (x-1) (y-1)) p) () xs
    freezeImage mi

foldMatrixM :: Monad m => (b -> Int -> Int -> a -> m b) -> b -> Matrix a -> m b
foldMatrixM f b xs = foldM (\b (y,x) -> f b y x (unsafeGet y x xs)) b pos
    where
    pos = [(x,y) | y <- [1..ncols xs], x <- [1..nrows xs]]

readBMP :: FilePath -> IO (Matrix PixelRGBA8)
readBMP file = do
    e <- readImage file
    case e of
        Left err -> error err
        Right dimg -> case dimg of
            ImageY8 i -> img2matrix $ promoteImage i
            ImageRGB8 i -> img2matrix $ promoteImage i
            ImageRGBA8 i -> img2matrix i

writeBMP :: FilePath -> Matrix PixelRGBA8 -> IO ()
writeBMP file xs = do
    i <- matrix2img xs
    writeBitmap file i

withBMP :: FilePath -> FilePath -> (Matrix PixelRGBA8 -> Matrix PixelRGBA8) -> IO ()
withBMP from to go = readBMP from >>= writeBMP to . go

whitePx  = PixelRGBA8 255 255 255 255
blackPx  = PixelRGBA8 0 0 0 255
redPx    = PixelRGBA8 255 0 0 255




