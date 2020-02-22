module MDIO where

import Data.Ratio ( (%) )
import Fraction 



linePivo,lineAnular::[Float]
linePivo = [0,1,0,3,-1,0,2,40]
lineAnular = [0,0,-1,1,-1,1,1,20]

linePivo',lineAnular'::[Frac]
linePivo'=[(Frac 0 1),(Frac 1 1),(Frac 0 1),(Frac 3 1),(Frac (-1) 1),(Frac 0 1),(Frac 2 1),(Frac 40 1)]
lineAnular'=[(Frac 0 1),(Frac 0 1),(Frac (-1) 1),(Frac 1 1),(Frac (-1) 1),(Frac 1 1),(Frac 1 1),(Frac 20 1)]

g'::Frac->(Frac,Frac)->Frac
g' factor (pivo,anular) = anular+factor*pivo 

f'::[Frac]
f' = map (g' x) linha 
   where 
   	linha = (zip linePivo' lineAnular')
   	x=(Frac (-1) 3)


g::Float->(Float,Float)->Float
g factor (pivo,anular) = anular+factor*pivo 

   
f::[Float]
f = map (g x) linha 
   where 
   	linha = (zip linePivo lineAnular)
   	x= (-1/3)


{-
readRational :: String -> Rational
readRational input = read intPart % 1 + read fracPart % (10 ^ length fracPart)
  where (intPart, fromDot) = span (/='.') input
        fracPart           = if null fromDot then "0" else tail fromDot
-}