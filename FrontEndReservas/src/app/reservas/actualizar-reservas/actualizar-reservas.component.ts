import { Component } from '@angular/core';
import { Reserva } from '../modelos/reserva';
import { Salon } from '../../salones/modelos/salon';
import { SalonService } from '../../salones/servicios/salon-service';
import { ReservaService } from '../servicios/reserva-service';
import { ActivatedRoute, Router } from '@angular/router';
import { response } from 'express';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
@Component({
  selector: 'app-actualizar-reservas',
  imports: [FormsModule, CommonModule, SweetAlert2Module, HttpClientModule],
  templateUrl: './actualizar-reservas.component.html',
  styleUrl: './actualizar-reservas.component.css'
})
export class ActualizarReservasComponent {
  public reserva: Reserva = new Reserva();
  public salones: Salon[] = [];
  public titulo: string = 'Actualizar reserva';

  constructor(
    private salonService: SalonService,
    private reservaService: ReservaService,
    private router: Router,
    private route: ActivatedRoute){}

  ngOnInit(): void {
    const reservaId = this.route.snapshot.paramMap.get('id');
    this.salonService.getSalones().subscribe(salones => {
      this.salones = salones;
      if(reservaId){
        this.reservaService.getReservaById(+reservaId).subscribe(reserva => {
          this.reserva = reserva;
          if(reserva.objSalon !== null && reserva.objSalon !== undefined){
            this.reserva.objSalon = this.salones.find(cat => cat.id == reserva.objSalon?.id) || null;
          }
        });
      }
    });
  }

  public actualizarReserva(): void {
    if (!this.validarActualizarReserva()) return;

    // Verificar disponibilidad del salón
    this.reservaService.verificarDisponibilidad(this.reserva).subscribe(
        (disponible) => {
            if (!disponible) {
                Swal.fire('Error', 'El salón ya está reservado en el horario especificado.', 'warning');
                return;
            }

            // Si el salón está disponible, proceder con la actualización
            console.log("Actualizando reserva ", this.reserva);
            this.reservaService.update(this.reserva).subscribe(
                (response) => {
                    console.log("Reserva actualizada exitosamente");
                    this.router.navigate(['reservas/listar-reservas']);
                    Swal.fire('Reserva actualizada', `Reserva de ${response.nombres} actualizada con éxito!`, 'success');
                },
                (error) => {
                    console.error("Error al actualizar la reserva: ", error);
                    Swal.fire('Error', 'No se pudo actualizar la reserva. Inténtalo de nuevo.', 'error');
                }
            );
        },
        (error) => {
            console.error("Error al verificar disponibilidad del salón", error);
            Swal.fire('Error', 'No se pudo verificar la disponibilidad del salón.', 'error');
        }
    );
}

  volverListadoReservas(): void {
        this.router.navigate(['/reservas/listar-reservas']);
  }

  //Validaciones formulario
  public validarActualizarReserva(): boolean {
      if (!this.reserva.nombres || this.reserva.nombres.length < 3) {
        Swal.fire('Error', 'No puedes dejar campos vacíos', 'warning');
        return false;
      }
      if (!this.reserva.apellidos || this.reserva.apellidos.length < 3) {
        Swal.fire('Error', 'No puedes dejar campos vacíos', 'warning');
        return false;
      }
  
      if (this.reserva.cantidadPersonas == null) {
        Swal.fire('Error', 'La cantidad de personas no puede estar vacía', 'warning');
        return false;
      } 
  
      if (this.reserva.cantidadPersonas <= 0) {
        Swal.fire('Error', 'La cantidad de personas no puede ser 0 o negativa', 'warning');
        return false;
      }
      
      if (!this.reserva.objSalon) {
        Swal.fire('Error', 'Debes seleccionar un salón', 'warning');
        return false;
      }
      if (!this.reserva.fechaReserva) {
        Swal.fire('Error', 'Debes seleccionar una fecha válida', 'warning');
        return false;
      }
      const hoy = new Date();
      hoy.setHours(0, 0, 0, 0);
      if(new Date(this.reserva.fechaReserva)<hoy){
        Swal.fire('Error', 'La fecha no puede ser menor que la del día de hoy', 'warning');
        return false;
      }
      if(!this.reserva.horaInicio){
        Swal.fire('Error', 'Debes seleccionar una hora de inicio', 'warning');
        return false;
      }
      if(!this.reserva.horaFin){
        Swal.fire('Error', 'Debes seleccionar una hora de finalización', 'warning');
        return false;
      }
      return true;
    } 
}
