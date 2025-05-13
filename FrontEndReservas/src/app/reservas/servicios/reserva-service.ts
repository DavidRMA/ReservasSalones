// Esta servicio se utiliza para consumir el endpoint de reservas desde el frontend.
import { Injectable } from "@angular/core";
import { Reserva } from "../modelos/reserva";
import { catchError, Observable, throwError } from "rxjs";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import Swal from "sweetalert2";

@Injectable({
    providedIn: 'root'
})
export class ReservaService {
    private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    private urlEndPoint: string = 'http://localhost:5000/api/reservas';

    constructor(private http: HttpClient) { }
    getReservas(): Observable<Reserva[]> {
        console.log("Listando reservas desde el servicio");
        return this.http.get<Reserva[]>(this.urlEndPoint);
    }

    create(reserva: Reserva): Observable<Reserva> {
        console.log("Creando reserva desde el servicio");
        return this.http.post<Reserva>(this.urlEndPoint, reserva, { headers: this.httpHeaders}).pipe(
            catchError(this.handleError)
        );
    }

    update(reserva: Reserva): Observable<Reserva> {
        console.log("Actualizando reserva desde el servicio", reserva);
        return this.http.put<Reserva>(`${this.urlEndPoint}/${reserva.id}`, reserva, { headers: this.httpHeaders}).pipe(
            catchError(this.handleError)
        );
    }

    deleteReserva(id: number): Observable<void> {
        console.log("Eliminando reserva desde el servicio");
        return this.http.delete<void>(`${this.urlEndPoint}/${id}`, { headers: this.httpHeaders}).pipe(
            catchError(this.handleError)
        );
    }

    getReservaById(id: number): Observable<Reserva> {
        console.log("Obteniendo reserva por ID desde el servicio", id);
        return this.http.get<Reserva>(`${this.urlEndPoint}/${id}`);
    }

    confirmarReserva(id: number): Observable<Reserva> {
        console.log("Confirmando reserva desde el servicio", id);
        return this.http.put<Reserva>(`${this.urlEndPoint}/confirmar/${id}`, { headers: this.httpHeaders}).pipe(
            catchError(this.handleError)
        );
    }

    cancelarReserva(id: number): Observable<Reserva> {
        console.log("Cancelando reserva desde el servicio", id);
        return this.http.put<Reserva>(`${this.urlEndPoint}/cancelar/${id}`, { headers: this.httpHeaders}).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        if(error.status == 400 || error.status == 404){
            const codigoError = error.error.codigoError;
            const mensajeError = error.error.mensaje;
            const codigoHttp = error.error.codigoHttp;
            const url = error.error.url;
            const metodo = error.error.metodo;

            console.error(`Error ${codigoHttp} en ${metodo} ${url}: ${mensajeError} (Código: ${codigoError})`);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: mensajeError,
                confirmButtonText: 'Cerrar'
            });

            return throwError(() => new Error(mensajeError));
        }else{
            return throwError(() => new Error('Ocurrió un error indeperado :('));
        }
    }

    verificarDisponibilidad(reserva: Reserva): Observable<boolean> {
        return this.http.post<boolean>(`${this.urlEndPoint}/verificar-disponibilidad`, reserva);
    }
}
