import { Component, Renderer2, ElementRef, Inject, ViewChild } from '@angular/core';
import * as bootstrap from 'bootstrap';
import { Reserva } from '../modelos/reserva';
import { ReservaService } from '../servicios/reserva-service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';



@Component({
  selector: 'app-ver-reservas',
  imports: [CommonModule],
  templateUrl: './ver-reservas.component.html',
  styleUrl: './ver-reservas.component.css'
})
export class VerReservasComponent {
  public reserva: Reserva = new Reserva();
  public reservaSeleccionada: Reserva | null = null; // Reserva seleccionada para el modal

  constructor(
    private reservaService: ReservaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const reservaId = this.route.snapshot.paramMap.get('id');
    if (reservaId) {
      this.reservaService.getReservaById(+reservaId).subscribe((reserva) => {
        this.reserva = reserva;
      });
    }
  }

  irListarReservas(): void{
    this.router.navigate(['/reservas/listar-reservas']);
  }
}
