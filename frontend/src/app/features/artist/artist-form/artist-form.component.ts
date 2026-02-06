import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ArtistFacade } from '../artist.facade';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-artist-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './artist-form.component.html'
})
export class ArtistFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  artistId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private artistFacade: ArtistFacade,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      nome: ['', Validators.required]
    });
  }

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && idParam !== 'new') {
      this.artistId = Number(idParam);
      this.isEdit = true;
      this.artistFacade.loadArtist(idParam);
      this.artistFacade.selectedArtist$.pipe(take(2)).subscribe(artist => {
        if (artist && artist.id === this.artistId) {
          this.form.patchValue({
            nome: artist.nome
          });
        }
      });
    }
  }

  async onSubmit() {
    if (this.form.invalid) return;

    const artistData = this.form.value;

    if (this.isEdit && this.artistId) {
      this.artistFacade.updateArtist(this.artistId.toString(), { id: this.artistId, ...artistData }).subscribe(() => {
        this.router.navigate(['/artists']);
      });
    } else {
      this.artistFacade.createArtist(artistData).subscribe(() => {
        this.router.navigate(['/artists']);
      });
    }
  }
}
