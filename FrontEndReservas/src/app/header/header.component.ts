import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  titulo: string = "Sistema de reservas";
  seccionUser: boolean = true;

  constructor(private router: Router){
    this.router.events.subscribe(()=>{
      this.seccionUser = !this.router.url.includes('/registrar-reservas');
    });
  }
}
