// Este servicio se utiliza para consumir el endpoint de salones desde el frontend. 
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Salon } from "../modelos/salon";

@Injectable({
    providedIn: 'root'
  })
export class SalonService {
    private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    private urlEndPoint: string = 'http://localhost:5000/api/salones/';

    constructor(private http: HttpClient) { }
    getSalones(): Observable<Salon[]> {
        console.log("Listando salones desde el servicio");
        return this.http.get<Salon[]>(this.urlEndPoint);
    }
}
