import { Routes } from '@angular/router';
import { RegistrarReservasComponent } from './reservas/registrar-reservas/registrar-reservas.component';

export const routes: Routes = [
    { path: '', redirectTo: 'reservas/registrar-reservas', pathMatch: 'full' },
    {path: 'reservas/registrar-reservas', component: RegistrarReservasComponent}
];
