import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  public columna1: any = {titulo: 'Universidad del Cauca', nit: 'NIT. 891500319-2', redesSociales: 'Siguenos en: '};
  public columna2: any = {titulo: 'Enlaces de interes', enlace1: 'Política de privacidad y tratamiento de datos personales', enlace2: 'Política de derechos de autor y/o autorización sobre uso de contenidos', enlace3: 'Canales físicos y electrónicos'}
  public columna3Fila1: any = {titulo: 'Contáctanos', direccion: 'Calle 5 # 4-70 | Popayán, Colombia', telefono: '+57(602) 820 9900', lineaGratuita: '018000 949020'}
  public columna3Fila2: any = {titulo: 'Horario de atención', horario: 'Lunes a jueves de 8:00 a.m. a 5:00 p.m. - Viernes de 8:00 a.m. a 4:00 p.m.'}
}