# NewsHeadsWithMVVMDagger
HeadLines app proto about listing the headlines, filtering categories and saving for offline display using MVVM arch with Dagger and Room DB

Used CustomImageLoader that hosts implementation of downloading and saving bitmaps in DiskLRUCache for offline image display.
And I also wrote my own implementation for downloading bitmaps and compression and also used Glide api for downloading and  saving image bitmaps .

# Code Approach

Preferred MVVM architecture and choosed repository layer as datalayer that communicates the ViewModels.

Views (updates ui) - > ViewModels (business calls ) - > Repository (data fetch)
Core.dagger -> AppLevel Component -> contains All modules at global level 
Single Unified model class -> for both cloud and local repo

# Code Map :
MainActivity - > NewsViewModel <- MainActivityModule (Provider) <- ActivityBuilder (Provider)

NewsDetailActivity - > NewsViewModel <- NewsDetailActivityModule (Provider) <- ActivityBuilder (Provider)

CloudRepo (Cloud api calls) <- NetworkModule (Provider)

DBRepo (Local database calls) <- DBModule (Provider)

HeadLine (Model) -> common model for Cloud and DB repos

CachedImageViewLoader - > Offline Image storage using DiskLruCache and LRUCache

To achieve :

UnitTest cases for dagger2 code is different from non dagger MVVM codebase. The later can be written unittest cases feasibly.

Still must do R&D and figure ou writing unit  testcases using dagger injection that executes livedata changes

# Instrumentation test 
Wrote Sample Instrumentation case for headlines category clicks and going to news detailed view.
