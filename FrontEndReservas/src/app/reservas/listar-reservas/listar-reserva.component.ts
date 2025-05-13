import { Component, Inject } from '@angular/core';
import { Reserva } from '../modelos/reserva';
import { ReservaService } from '../servicios/reserva-service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listar-reserva',
  imports: [CommonModule],
  templateUrl: './listar-reserva.component.html',
  styleUrl: './listar-reserva.component.css'
})
export class ListarReservaComponent {
  reservas: Reserva[] = [];
  reservaSeleccionada!: number;

  constructor(private objReservaService: ReservaService, private router: Router) {}

  ngOnInit(): void {
    this.objReservaService.getReservas().subscribe
    (
      reservas => {
        console.log("Listando reservas");
        this.reservas = reservas;
      }
    );
  }

  confirmarReserva(id: number): void {
    Swal.fire({
      title: '¿Está seguro de que desea confirmar la reserva?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Aceptar',
      cancelButtonText: 'Rechazar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.objReservaService.confirmarReserva(id).subscribe(
          (updatedReserva) => {
           // Actualizar el estado de la reserva en la lista
          const reservaIndex = this.reservas.findIndex(reserva => reserva.id === id);
          if (reservaIndex !== -1) {
            this.reservas[reservaIndex].estadoReserva = updatedReserva.estadoReserva;
          }
            Swal.fire(
              'Reserva confirmada',
              'La reserva ha sido confirmada correctamente.',
              'success'
            );
          },
          (error) => {
            // Manejo del error al confirmar la reserva
            console.error('Error al confirmar la reserva:', error);
            Swal.fire(
              'Error',
              'Hubo un problema al confirmar la reserva. Inténtelo de nuevo.',
              'error'
            );
          }
        );
      } else {
        console.log("Confirmación de reserva cancelada");
      }
    });
  }
  

  cancelarReserva(id: number): void {
    Swal.fire({
      title: '¿Está seguro de que desea cancelar la reserva?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Aceptar',
      cancelButtonText: 'Rechazar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.objReservaService.cancelarReserva(id).subscribe(
          (updatedReserva) => {
            // Actualizar el estado de la reserva en la lista
            const reservaIndex = this.reservas.findIndex(reserva => reserva.id === id);
            if (reservaIndex !== -1) {
              this.reservas[reservaIndex].estadoReserva = updatedReserva.estadoReserva;
            }
            Swal.fire(
              'Reserva cancelada',
              'La reserva ha sido cancelada correctamente.',
              'success'
            );
          }
        );
      }else{
        console.log("Cancelación de reserva cancelada");
      }
    });
  }

  editarReserva(id: number): void {
    this.router.navigate(['/reservas/actualizar-reservas', id]);
  }

  verReserva(id: number): void {
    this.router.navigate(['/reservas/ver-reservas', id]);
  }

  irRegistrarReservas(): void {
    this.router.navigate(['/reservas/registrar-reservas']);
  }
}
