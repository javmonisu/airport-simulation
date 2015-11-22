# Airport Simulation
Homework from Simulation Methods subject from the UPM AI MSc.

Simulación de sucesos discretos. 
A un aeropuerto llegan aviones de diferentes destinos según un proceso de Poisson no homogéneo con la siguiente tasa:


![alt tag](http://i.imgur.com/CKCo8hu.png)

Estos aviones se quedan esperando en vuelo hasta que la torre de control le asigne pista, realizando entonces la maniobra de aterrizaje que les lleva un tiempo distribuido según una normal de media 10 minutos y desviación típica 3.

Una vez el avión ha aterrizado, espera a que un vehículo lo guíe hacia el puesto de desembarco de pasajeros, estimándose un tiempo de traslado exponencial de tasa 30 aviones por hora. Los traslados del vehículo sin avión se suponen despreciables.

Por otra parte, se programa la salida de aviones del aeropuerto (no tienen nada que ver con los aviones que aterrizan). El tiempo que tardan en desembarcar pasajeros y equipaje y en embarcar los nuevos y desplazarse a la zona donde pueden iniciar la maniobra de despegue se distribuye exponencialmente con media 45 minutos. Allí, esperan a que la torre de control les asigne una pista y les permita realizar dicha maniobra cuya duración sigue una uniforme entre 10 y 15 minutos (los aterrizajes tendrán preferencias sobre los despegues).
En el aeropuerto que se desea estudiar existen 3 pistas que sirven tanto para aterrizar como para despegar. La torre de control da prioridad a los aviones que desean aterrizar frente a los que desean despegar. El número de vehículos guía está limitado por 20 vehículos.

Suponiendo que las pistas quedan libre una vez el avión haya terminado el aterrizaje y que el aeropuerto está inicialmente vacío de aviones, simule el comportamiento del aeropuerto durante un mes y estime las principales medidas de rendimiento del mismo (tiempos medios y máximos de espera de los aviones para aterrizar y despegar, número medio de aviones en el aeropuerto, porcentaje de tiempo que están ocupadas las pistas).
Analice la posibilidad de construir una nueva pista y de disponer de 5 vehículos guía adicionales.
