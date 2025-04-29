import { Component } from '@angular/core';
import { Reserva } from '../modelos/reserva';
import { Salon } from '../../salones/modelos/salon';
import { SalonService } from '../../salones/servicios/salon-service';
import { ReservaService } from '../servicios/reserva-service';
import { Router } from '@angular/router';
import { response } from 'express';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-registrar-reservas',
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './registrar-reservas.component.html',
  styleUrl: './registrar-reservas.component.css'
})
export class RegistrarReservasComponent {
  public reserva: Reserva = new Reserva();
  public salones: Salon[] = [];
  public titulo: string = "Registrar Reserva";

  constructor(private salonService: SalonService, private reservaService: ReservaService, private router: Router) { }
  
  ngOnInit(): void {
    this.reserva.objSalon = null;
    this.salonService.getSalones().subscribe(
      salones => this.salones = salones
    );
  }

  public registrarReserva(){
    console.log("Registrando reserva");
    this.reservaService.create(this.reserva).subscribe(
      {
        next: (response) => {
          console.log("Reserva registrada exitosamente!");
          console.log(this.reserva);
          this.router.navigate(['/reservas/listarReservas']),
          Swal.fire('Nueva Reserva', `Reserva para ${response.nombres} registrada con éxito`, 'success');
        },
        error: (err) => {
          console.error("Error al registrar la reserva", err.message);
        }
      }
    );
  }
}
