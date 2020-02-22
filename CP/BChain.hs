-- (c) MP-I (1998/9-2006/7) and CP (2005/6-2018/19)

module BlockChain where

import Cp
import Data.Monoid
import Control.Applicative
import List

-- (1) Datatype definition -----------------------------------------------------

data BlockChain = Bc {bc::Block} | Bcs {bcs::(Block,BlockChain)} deriving Show

type Block 	   = (MagicNo,(Time,Transactions))
type Transaction  = (Entity,(Value,Entity))
type Transactions = [Transaction]

type Ledger = [(Entity,Value)]

type MagicNo = String
type Time    = Int
type Entity  = String
type Value   = Int


inBlockChain = either Bc Bcs

outBlockChain :: BlockChain -> Either bc (bc,BlockChain)
outBlockChain ( Bc(MagicNo,(Time,Transactions)) )       = i1 (Bc(MagicNo,(Time,Transactions)) )
outBlockChain ( Bcs(MagicNo,(Time,Transactions)), ) = i2 (t1,t2)

-- (2) Ana + cata + hylo -------------------------------------------------------

recBlockChain f = baseLTree id f          -- that is:  id -|- (f >< f)

baseBlockChain g f = g -|- (f >< f)

cataBlockChain g = g . (recLTree (cataLTree g)) . outLTree

anaBlockChain f = inLTree . (recLTree (anaLTree f) ) . f

hyloBlockChain a c = cataLTree a . anaLTree c

-- (3) Map ---------------------------------------------------------------------

instance Functor BlockChain
         where fmap f = cataBlockChain ( inLTree . baseLTree f id )
