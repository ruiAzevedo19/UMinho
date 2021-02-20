module Generator where

import Test.QuickCheck 
import Control.Monad.Trans.Maybe

type Code = Integer
type Name = String
type Coords = (Float,Float)
type Radius = Float
type NIF = Integer
type Price = Float 
type Quantity = Float 
type Description = String
type Weight = Float

path :: FilePath
path = "/Users/etiennecosta/Desktop/ats/Generator/Logs/"

data User = User Code Name Coords
	deriving (Show)

data Volunteer = Volunteer Code Name Coords Radius 
     deriving (Show)

data Transporter = Transporter Code Name Coords  NIF Radius Price
     deriving (Show)

data Shop = Shop Code Name Coords
     deriving (Show)

data Product = Product Code Description Quantity Price
          deriving (Show)

data Order = Order Code Code  Code Weight 
          deriving (Show)

data Accepted = Accepted Code 
     deriving (Show)

data Structure = Structure [User] [Volunteer] [Transporter] [Shop] [Order] [Product] [Accepted]
               deriving (Show)

firstNames :: [Name]
firstNames =["Etienne","Rui","Mauricio","Maria","Hugo","Rodrigo","Guilherme","Francisco","Pedro","Carlos","Cristiano","Joana","Andre","Diogo","Paula","Paulo","Nuno","Jose","Rafael","Rita","Sofia","Ana","Vitor"]

genLastName :: Gen Name
genLastName = frequency [(321,return "Correia"),(596,return "Barbosa"),(777,return "Cardoso"),(381,return "Ferreira"),(371,return "Miranda"),(299,return "Valente"),(525,return "Vila"),(125,return "Araujo"),(555,return "Pinheiro"),(222,return "Reis"),(488,return "Pereira"),(368,return "Costa"),(197,return "Carvalho"),(237,return "Alves"),(637,return "Azevedo")]

transporterNames :: Gen Name
transporterNames = frequency [(16,return "TERCARGO - TRANSITARIOS"),(19,return "TORRESTIR TRANSITARIOS LDA"),(22,return "SABIA ATITUDE - CONSTRUCOES"),(29,return "ZENITHINDEX"),(35,return "DHL"),(55,return "CTT")]

shopNames ::Gen Name
shopNames = frequency [(593,return "TV e Telecomunicações"),(333,return "Primark"),(93,return "Punt Roma"),(222,return "ColchaoNet.com"),(100,return "Fnac"),(150,return "Worten")]

productsNames :: [Name]
productsNames = ["Farinha de trigo","Molho de pimenta","Detergente","Banana","Leite condensado","Leite integral litro","Saco de lixo 50l","Sumo garrafa 1l","Batata","Creme de leite","Ovos","Tira manchas","Beterraba","Farofa pronta","Sumo caixa 500ml","Saco de lixo 30l","Peixe","Macarrao","Desinfetante","Frango","Farinha de mandioca","Farofa pronta","Espinafre","Ervilha","Farinha de trigo","Cebola","Farofa pronta","Sabao em pedra","Desinfetante","Iogurte","Carne seca","Condicionador","Extrato de tomate","Queijo","Alface","Lustra moveis","Manteiga","Leite desnatado litro","Agua sanitaria","Salsa","Shampoo","Farinha de milho","Atum","Bolacha","Esponja de aco","Feijao 2kg","Milho verde","Uva","Couve","Queijo Mussarela","Margarina","Salsicha","Goiabada","Tira manchas","Cenoura","Abacate","Limpa vidros","Melancia","Sal","Acucar","Sabonete","Sardinha","Alcool"]



genName:: Gen Name
genName = do 
	            fn  <- elements firstNames
	            ln  <- genLastName
	            return (fn++" "++ln)


genCodes::  [Code] -> Int -> Gen [Code]
genCodes codes 0 = return codes 
genCodes [] n = do 
                         code <- elements [1..10000]
                         genCodes [code] (n-1)
genCodes codes n = do 
                         code <- elements [1..10000]
                         if elem code codes then genCodes codes n else genCodes (code:codes) (n-1)

genCoords:: Gen Coords
genCoords = do 
                latitude  <- choose(-90,90)
                longitude <- choose(-180,180)
                return (latitude,longitude)

genRadius:: Gen Float
genRadius = do 
     radius <- choose(1,200)
     return radius

genWeight :: Gen Weight
genWeight = do 
     weight <- choose(0::Float,100::Float)
     return weight

genPrice:: Gen Price
genPrice = do
     price <- choose(0::Float, 5::Float)
     return price

genNifs::  [NIF] -> Int -> Gen [NIF]
genNifs nifs 0 = return nifs 
genNifs [] n = do 
          nif <- choose (111111111,999999999)
          genNifs [nif] (n-1)
genNifs nifs n = do 
          nif <- choose (111111111,999999999)
          if elem nif nifs then genNifs nifs n else genNifs (nif:nifs) (n-1)


genUser :: Code -> Gen User 
genUser code = do
                    name <- genName
                    coords <- genCoords
                    return (User code name coords)

genUsers :: [Code] -> Gen [User]
genUsers [] = return []
genUsers (h:t) = do 
                     x <- genUser h
                     xs <- genUsers t
                     return (x:xs)


genVolunteer :: Code -> Gen Volunteer
genVolunteer code = do  name <- genName
                        coords <- genCoords
                        radius <- genRadius
                        return (Volunteer code name coords radius)

genVolunteers :: [Code] -> Gen [Volunteer]
genVolunteers [] = return []
genVolunteers (h:t) = do 
              x <- genVolunteer h
              xs <- genVolunteers t
              return (x:xs)  

genTransporter :: NIF -> Code->  Gen Transporter
genTransporter nif code = do name   <-  transporterNames
                             coords <- genCoords
                             radius <- genRadius
                             price  <- genPrice
                             return (Transporter code name coords nif radius price)

genTransporters :: [NIF] ->[Code]-> Gen [Transporter]
genTransporters  [] [] = return []
genTransporters  (h:t) (l:ls) = do   x <- genTransporter h l
                                     xs <- genTransporters  t ls
                                     return (x:xs)

genShop :: Code -> Gen Shop
genShop code = do name <- shopNames
                  coords <- genCoords
                  return (Shop code name coords )

genShops :: [Code]-> Gen [Shop]
genShops  []  = return []
genShops  (h:t)  = do 
                      x <- genShop h 
                      xs <- genShops  t 
                      return (x:xs)

genProduct:: Code -> Gen Product  
genProduct code = do 
                    description <- elements productsNames
                    quantity    <- choose(0::Float, 5::Float)
                    price       <- choose(0::Float, 5::Float)
                    return (Product code description quantity price )

genProducts:: [Code] -> Gen [Product]
genProducts [] = return []
genProducts (h:t) = do 
                      x <-  genProduct  h
                      xs <- genProducts t 
                      return (x:xs)

                        
genOrder :: Code -> Code -> Code  -> Gen Order
genOrder codOrder codUser codShop  = do      
                                            weight <- genWeight
                                            return (Order codOrder codUser codShop weight )
                                                   

genOrders :: [Code]->[Code]->[Code]-> Gen [Order]
genOrders [] _ _  = return []
genOrders _ [] _  = return []
genOrders _ _ []  = return []
genOrders (a:b) (c:d) (e:f)  = do
                                        x  <- genOrder a c e 
                                        xs <- genOrders b d f  
                                        return (x:xs)


genAccepted :: Code -> Gen Accepted
genAccepted code = do 
                      return (Accepted code)

genAccepteds :: [Code] -> Gen [Accepted]
genAccepteds [] = return []
genAccepteds (h:t) = do 
                      x  <-  genAccepted  h
                      xs <- genAccepteds t 
                      return (x:xs)

----------------------------------------------------------------------------------------------------------------------------------------

usersToString :: [User] -> String 
usersToString [] = []
usersToString ((User code name coords) : t) = "Utilizador:"++ "u"++ (show code)
                                              ++ "," ++ name ++ ","
                                              ++ (show (fst coords)) ++ ","
                                              ++ (show (snd coords)) ++ "\n"
                                              ++ usersToString t 

volunteersToString :: [Volunteer] -> String 
volunteersToString [] = []
volunteersToString ((Volunteer code name coords radius): t) = "Voluntario:"++ "v" ++ (show code)
                                                            ++ "," ++ name ++ ","
                                                            ++ (show (fst coords)) ++ ","
                                                            ++ (show (snd coords)) ++ ","
                                                            ++ (show radius) ++"\n" ++ 
                                                            volunteersToString t 

transportersToString :: [Transporter] -> String
transportersToString [] = []
transportersToString ((Transporter code name coords nif radius price): t) = "Transportadora:"++"t"++ (show code)
                                                                           ++ ","++ name ++ "," ++ (show (fst coords))
                                                                           ++ "," ++ (show (snd coords)) ++ ","
                                                                           ++ (show nif) ++ "," ++ (show radius)++ ","
                                                                           ++ (take 4 (show price)) ++ "\n" ++ transportersToString t 


productToString :: Product -> String 
productToString ((Product  code description quantity price )) = "p"++(show code)++","++
                                                                    description ++","++(show quantity)++
                                                                    ","++(show price) 


shopsToString :: [Shop] -> String
shopsToString [] = []
shopsToString ((Shop code name coords) : t) = "Loja:"++"l"++ (show code)
                                              ++ ","++ name ++ "," ++ (show (fst coords))
                                              ++ "," ++ (show (snd coords)) ++ "\n"++shopsToString t
                                              



ordersToString :: [Order] -> [Product] -> String 
ordersToString [] _ = []
ordersToString _ [] = []
ordersToString ((Order codOrder codUser codShop weight ):t) (x:xs) = "Encomenda:"++"e"++(show codOrder)++","
                                                              ++"u"++(show codUser)++","++"l"++(show codShop)
                                                              ++","++(show weight)++","++(productToString x)++"\n"++ordersToString t xs

acceptedToString :: [Accepted] -> String
acceptedToString [] = []
acceptedToString ((Accepted code):t) = "Aceite:"++"e"++(show code)++"\n"++acceptedToString t 

structureToString :: Structure -> [String]
structureToString (Structure users volunteers transporters shops orders products accepteds ) = return (
                                               (usersToString users) ++
                                               (volunteersToString volunteers)++
                                               (transportersToString transporters)++
                                               (shopsToString shops)++
                                               (ordersToString orders products)++
                                               (acceptedToString accepteds)
                                               )

genLogFile :: (Int,Int,Int,Int,Int,Int,Int)-> Gen Structure
genLogFile (nrUsers,nrVolunteers,nrTransporters,nrShops,nrOrders,nrProducts,nrAccepteds)= do  
                                                                                               usersCodes        <-  genCodes [] nrUsers --nrUsers
                                                                                               volunteersCodes   <-  genCodes [] nrVolunteers  --nrVolunteers
                                                                                               transportersCodes <-  genCodes [] nrTransporters  --nrTransporters
                                                                                               shopsCodes        <-  genCodes [] nrShops  --nrShops
                                                                                               productsCodes     <-  genCodes [] nrProducts --nrProducts
                                                                                               nifs              <-  genNifs  [] nrTransporters  --nrTransporters
                                                                                               ordersCodes       <-  genCodes [] nrOrders  --nrOrders
                                                                                               products          <-  genProducts productsCodes
                                                                                               users             <-  genUsers usersCodes
                                                                                               volunteers        <-  genVolunteers volunteersCodes
                                                                                               transporters      <-  genTransporters nifs transportersCodes
                                                                                               shops             <-  genShops shopsCodes
                                                                                               orders            <-  genOrders ordersCodes usersCodes shopsCodes
                                                                                               accepteds         <-  genAccepteds (take nrAccepteds ordersCodes)
                                                                                               return (Structure users volunteers transporters shops orders products accepteds)


genLogFiles :: Int -> (Int,Int,Int,Int,Int,Int,Int)-> Gen [Structure]
genLogFiles 0  _ = return []
genLogFiles n (nrUsers,nrVolunteers,nrTransporters,nrShops,nrOrders,nrProducts,nrAccepteds) = do 
                                                                                               h <- genLogFile (nrUsers,nrVolunteers,nrTransporters,nrShops,nrOrders,nrProducts,nrAccepteds)
                                                                                               t <- genLogFiles (n-1) (nrUsers,nrVolunteers,nrTransporters,nrShops,nrOrders,nrProducts,nrAccepteds)
                                                                                               return (h:t)


writeLogs :: Int -> [Structure] -> IO()
writeLogs 0 [] = return ()
writeLogs n (h:t) = do 
               let content' = (structureToString h)
               let log = concat content' 
               writeFile (path++"log"++show(n)++".txt") log
               writeLogs (n-1) t 


main :: IO ()
main = do 
          putStrLn "Indique o número de Logs"
          n                <- getLine
          putStrLn "Indique o número de Utilizadores"
          nrUsers          <- getLine
          putStrLn "Indique o número de Voluntários"
          nrVolunteers     <- getLine
          putStrLn "Indique o número de Transportadoras"
          nrTransporters   <- getLine
          putStrLn "Indique o número de Lojas"
          nrShops          <- getLine
          putStrLn "Indique o número de Encomendas"
          nrOrders         <- getLine   
          putStrLn "Indique o número de Produtos"
          nrProducts       <- getLine          
          putStrLn "Indique o número de Encomendas Aceites"
          nrAccepteds      <- getLine

          let number = read n :: Int
          let nu = read nrUsers :: Int
          let nv = read nrVolunteers :: Int
          let nt = read nrTransporters :: Int
          let ns = read nrShops :: Int
          let no = read nrOrders :: Int
          let np = read nrProducts :: Int
          let na = read nrAccepteds :: Int

          structures <- generate (genLogFiles number (nu,nv,nt,ns,no,np,na))
          writeLogs number structures
          putStrLn ("Log files successfully created !")

