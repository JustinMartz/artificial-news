-- =========================================================
--  Test seed data for artificial_news
--  Equivalent to LoadDatabase.java
-- =========================================================

-- Clean tables before insert (optional for tests)
DELETE FROM article;
DELETE FROM article_photo;

-- =========================================================
--  Insert into article_photo
-- =========================================================
INSERT INTO article_photo (id, fullsize, thumbnail, caption, photographer)
VALUES
  (
    '33ba3fb5-53bd-4596-a668-86431bbfb94c',
    'Diving-into-the-Rhythm-Miami''s-Unique-Underwater-Jazz-Festival-1762037109422.png',
    'Diving-into-the-Rhythm-Miami''s-Unique-Underwater-Jazz-Festival-1762037109422-thumbnail.png',
    'Jazz musicians perform underwater at Miami Seaquarium, captivating audiences with a unique musical experience.',
    'Derek Chau'
  ),
  (
    '988a9c83-960b-4acd-9415-5c6a5e5931f2',
    'The-Mysterious-Allure-of-Orlando’s-Haunted-Swings-1762038011011.png',
    'The-Mysterious-Allure-of-Orlando’s-Haunted-Swings-1762038011011-thumbnail.png',
    'The haunted swings in Lake Eola Heights sway under the mysterious Orlando skies.',
    'Lucas Fernandez'
  ),
  (
    'e27a6247-0a76-4acb-a5ac-4d19c48e9947',
    'Feathers-Fly-and-Feet-Groove-at-Detroit''s-Disco-Pigeon-Festival-1762038932894.png',
    'Feathers-Fly-and-Feet-Groove-at-Detroit''s-Disco-Pigeon-Festival-1762038932894-thumbnail.png',
    'Festival-goers dance beneath a dazzling pigeon-themed light show at the Disco Pigeon Festival in Detroit.',
    'Samantha Kim'
  );

-- =========================================================
--  Insert into article
-- =========================================================
INSERT INTO article (
  id,
  created_at,
  dateline,
  headline,
  author,
  author_photo,
  article_body,
  provider,
  model,
  creation_time,
  article_photo_id
)
VALUES
  (
    'e4885aa2-7e74-42ed-9160-008ccbef9cc9',
    '2025-11-01T08:16:54.519613Z',
    'November 1, 2025 • 4:45 PM',
    'Diving into the Rhythm: Miami''s Unique Underwater Jazz Festival',
    'Alicia Martinez',
    'Alicia-Martinez-1762037109267.png',
    'In an audacious blend of music and marine life, Miami hosted its first-ever Underwater Jazz Festival, bringing new meaning to the term ''immersive experience.'' Held at the Miami Seaquarium, the event took place this past weekend and featured a collection of renowned jazz musicians performing beneath the waves. Concertgoers donned snorkeling gear and descended into the aquamarine depths, where they enjoyed the sultry sounds of saxophones and trumpets while surrounded by colorful schools of fish. "It''s unlike anything I''ve ever experienced," said festival attendee and jazz enthusiast Carlos Rivera. "The music, the setting, it was absolutely magical."\n\nThis groundbreaking event was the brainchild of local event organizer, Jasmine Lee, who sought to create a festival that would highlight Miami''s unique position as a city between the sea and vibrant cultural artistry. "I wanted to create an event that not only celebrates the incredible music scene we have here but also our deep connection to the ocean," Lee explained. Musicians played from waterproof stages encased in glass, a spectacle that amazed the 500 attendees, many of whom were visiting Miami specifically for the festival.\n\nThe festival not only promoted the jazz scene but also raised awareness for marine conservation efforts. Partnering with the Marine Conservation Institute, the event underscored the importance of preserving Miami''s natural underwater habitats. "We''re thrilled to be part of something that''s both entertaining and meaningful," said Sarah Thompson, head of the organization. "Combining jazz with the ocean environment reminds us how precious our marine ecosystems are and how they deserve our collective efforts to protect them." With plans for future festivals already underway, the Underwater Jazz Festival is set to become a celebrated fixture of Miami''s cultural calendar, merging music with messages of ecological stewardship.',
    'OpenAI',
    'gpt-4o',
    24951,
    '33ba3fb5-53bd-4596-a668-86431bbfb94c'
  ),
  (
    '095260fe-8361-4930-a9f7-091bf9ec12aa',
    '2025-11-02T08:38:39.969026Z',
    'November 2, 2025 • 5:00 PM',
    'The Mysterious Allure of Orlando’s Haunted Swings',
    'Jasmine Lee',
    'Jasmine-Lee-1762038010879.png',
    'In the heart of the vibrant city of Orlando, a unique phenomenon has been drawing curious thrill-seekers and paranormal enthusiasts alike: the haunted swings. These seemingly ordinary playground fixtures have become an enigma, captivating residents and visitors with tales of their supernatural activity. It all started in the suburb of Lake Eola Heights, where locals reported the swings moving on their own, even in the absence of any wind, a curiosity that turned into a legend.\n\nMichelle Thompson, a mother of two who frequents the playground, recounted her eerie experience. "It was a quiet afternoon, and my kids were playing nearby when I noticed the swings just started moving by themselves. I thought someone had given them a push, but no one was around," she said, her voice tinged with both excitement and unease. This playground has since become a hotspot for ghost hunters and tourists hoping to witness the swings in action.\n\nExperts offer differing explanations for the haunted swings of Orlando. Dr. Alan Rodriguez, a physicist at the University of Central Florida, suggests that microclimates and unique wind currents could be responsible. However, local historian Amanda Green passionately argues that the swings'' peculiar behavior stems from the area''s rich history and the spirits of children who once played there. Whether the phenomenon is a result of science or the supernatural, the haunted swings have undeniably become a charmingly spooky staple in Orlando’s cultural tapestry.',
    'OpenAI',
    'gpt-4o',
    25957,
    '988a9c83-960b-4acd-9415-5c6a5e5931f2'
  ),
  (
    'b0297a64-c0a7-481d-b950-5125da66e5e0',
    '2025-11-03T08:43:26.153504Z',
    'November 3, 2025 • 5:15 PM',
    'Feathers Fly and Feet Groove at Detroit''s Disco Pigeon Festival',
    'Jamie Rivera',
    'Jamie-Rivera-1762038932758.png',
    'In an exuberant celebration of music, dance, and feathered flair, the annual Disco Pigeon Festival swooped into Detroit this past weekend, drawing crowds from across the Midwest. Held in Hart Plaza, the event, now in its fifth year, featured a plethora of DJs spinning classic disco tracks, while attendees dressed in sparkly costumes mingled with live pigeons, all adorned in hues reminiscent of the grooviest decade. The festival aimed to fuse the city''s rich musical heritage with an unusual appreciation for its urban avian residents.\n\n"We wanted to create a unique experience that captures the essence of Detroit''s music scene while also highlighting the often-overlooked beauty of pigeons," explained festival founder and organizer, Benji Williams. Attendees were treated to impressive dance performances, interactive avian arts and crafts booths, and even a pigeon parade, where birds strutted their stuff to the beats of their own soundscape. The spectacle was not just about entertainment; it served to educate the public on the ecological importance of pigeons and their role in urban environments.\n\nThe highlight of the festival was undoubtedly the evening''s grand finale, a synchronized light show and dance-off dubbed the "Pigeon Boogie." As flocks of pigeons took flight, their feathers catching the multicolored lights, the crowd beneath them danced in jubilant unity. "It''s an extraordinary scene," said Laura Chen, a festival-goer and self-proclaimed pigeon enthusiast. "Only in Detroit can you find such a wonderfully weird and rewarding blend of community, culture, and creativity." Plans for next year''s festival are already underway, promising even more avian-themed surprises for all to enjoy.',
    'OpenAI',
    'gpt-4o',
    24020,
    'e27a6247-0a76-4acb-a5ac-4d19c48e9947'
  );