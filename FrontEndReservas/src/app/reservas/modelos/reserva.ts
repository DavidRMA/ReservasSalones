// Esta clase contiene la estructura de los datos de una reserva
import { Salon } from "../../salones/modelos/salon";

export class Reserva {
    id!: number;
    nombres!: string;
    apellidos!: string;
    cantidadPersonas!: number;
    fechaReserva!: Date;
    horaInicio!: string;
    horaFin!: string;
    estadoReserva!: string;
    objSalon: Salon | null = null; 
}
