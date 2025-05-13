import { Component } from '@angular/core';
import { Reserva } from '../modelos/reserva';
import { Salon } from '../../salones/modelos/salon';
import { SalonService } from '../../salones/servicios/salon-service';
import { ReservaService } from '../servicios/reserva-service';
import { Router } from '@angular/router';
import { response } from 'express';
import Swal from 'sweetalert2';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-registrar-reservas',
  imports: [FormsModule, CommonModule, HttpClientModule, ReactiveFormsModule],
  templateUrl: './registrar-reservas.component.html',
  styleUrl: './registrar-reservas.component.css'
})
export class RegistrarReservasComponent {
  public reserva: Reserva = new Reserva();
  public salones: Salon[] = [];
  public titulo: string = "Realiza tu reserva ahora!";

  constructor(private salonService: SalonService, private reservaService: ReservaService, private router: Router) { }
  
  ngOnInit(): void {
    this.reserva.objSalon = null;
    this.salonService.getSalones().subscribe(
      salones => this.salones = salones
    );
  }

  public registrarReserva(){
    if (!this.validarReserva()) return;
    this.reservaService.verificarDisponibilidad(this.reserva).subscribe(
        (disponible) => {
            if (!disponible) {
                Swal.fire('Error', 'El salón ya está reservado en el horario especificado.', 'warning');
                return;
            }
            // Si el salón está disponible, registrar la reserva
            console.log("Registrando reserva");
            this.reservaService.create(this.reserva).subscribe(
                {
                    next: (response) => {
                        console.log("Reserva registrada exitosamente!");
                        console.log(this.reserva);
                        this.router.navigate(['/reservas/listar-reservas']),
                        Swal.fire('Nueva Reserva', `Reserva para ${response.nombres} registrada con éxito`, 'success');
                    },
                    error: (err) => {
                        console.error("Error al registrar la reserva", err.message);
                    }
                }
            );
        },
        (error) => {
            console.error("Error al verificar disponibilidad del salón", error);
            Swal.fire('Error', 'No se pudo verificar la disponibilidad del salón.', 'error');
        }
    );
  }

  irListarReserva(): void{
    this.router.navigate(['/reservas/listar-reservas']);
  }

  public validarReserva(): boolean {
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

