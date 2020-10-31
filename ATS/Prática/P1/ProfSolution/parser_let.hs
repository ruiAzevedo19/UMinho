module Parser_Let where 

import Parser 

data Let = Let Items Exp deriving Show

type Items = [Item]

data Item  = NestedLet String Let
           | Atrib String Exp
           deriving show

type Exp = String

plet = f <$$>  token' "let" <++> symbol' symbol '{' <++> items <++> symbol' '}' <++> token' "in" <++> exp
    where f a b c d e f = Let c f 

plet' = f <$$>  token' "let" <++> enclosedBy (symbol' '{') items (symbol' '}') <++> token' "in" <++> exp
    where f a b c d = Let b d 

items =   f <$$> item 
     <|>  g <$$> item <++> symbol ';' <++> items
     where f a = [a]
           g a b c = a:c

items' = separatedBy item (symbol' ';')


item =  f <$$> ident <++> symbol' '=' <++> exp
    <|> g <$$> ident <++> symbol' '=' <++> plet 
    where f a b c = Atrib a c 
          g a b c = NestedLet a c 

exp = ident 
   <|> pNat

ident = f <$$> oneOrMore (satisfy isAlpha) <++> spaces
    where f a b = a

pInt = (\a b -> a) <++> oneOrMore (sastisfy isDigit) <++> spaces
