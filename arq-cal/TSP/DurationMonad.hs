module DurationMonad where

-- Defining a monad (the duration monad) --

data Duration a = Duration (Int, a) deriving (Show,Eq)

getDuration :: Duration a -> Int
getDuration (Duration (d,x)) = d

getValue :: Duration a -> a
getValue (Duration (d,x)) = x

instance Ord a => Ord (Duration a) where
  a <= b = (getDuration a) <= (getDuration b)
  compare a b = compare (getDuration a) (getDuration b)

instance Functor Duration where
  fmap f (Duration (i,x)) = Duration (i,f x)

instance Applicative Duration where
  pure x = (Duration (0,x))
  (Duration (i,f)) <*> (Duration (j, x)) = (Duration (i+j, f x))
  
instance Monad Duration where
    (Duration (i,x)) >>= k = Duration (i + (getDuration (k x)), getValue (k x))
    return x = (Duration (0,x))

wait1 :: Duration a -> Duration a
wait1 (Duration (d,x)) = Duration (d+1,x)

wait :: Int -> Duration a -> Duration a
wait i (Duration (d,x)) = Duration (i + d, x) 
