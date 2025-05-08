// Esta clase contiene la estructura de los datos de una reserva
import { Salon } from "../../salones/modelos/salon";
import { LocalTime } from "./local-time";

export class Reserva {
    id!: number;
    nombres!: string;
    apellidos!: string;
    cantidadPersonas!: number;
    fechaReserva!: Date;
    horaInicio!: LocalTime;
    horaFin!: LocalTime;
    estadoReserva!: string;
    objSalon: Salon | null = null; 
}
